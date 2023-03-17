import cfscrape, bs4
from bs4 import BeautifulSoup
import requests
# import collections 
# collections.Callable = collections.abc.Callable
#recipe1點入的食譜連結後進入的畫面，取得該食譜的詳細資訊
url = ""


def input_url(urlfromrecipe1):
    global url
    url = urlfromrecipe1



    #ingredients食材、amounts食材份量
def get_ingredientsandamounts():
    scraper2 = cfscrape.create_scraper()
    result2 = scraper2.get(url)
    soup = bs4.BeautifulSoup(result2.text, "html.parser")
    ingredients = soup.find_all("a", class_="ingredient-search")
    ingredientsarray=[]
    for ingredient in ingredients:
        i = ingredient.text.strip()
        ingredientsarray.append(i)
    amounts = soup.find_all("div", class_="ingredient-unit")
    amountsarray = []
    for amount in amounts:
        a = amount.text.strip()
        amountsarray.append(a)
    results1 = ""
    for i in range(len(ingredientsarray)):
        results1 += (ingredientsarray[i]+","+amountsarray[i] + ",")
    # print(results1)
    return results1[:len(results1)-1]

#steps步驟、images步驟對應的照片
def get_stepsandimagesurls():
    scraper2 = cfscrape.create_scraper()
    result2 = scraper2.get(url)
    soup = bs4.BeautifulSoup(result2.text, "html.parser")
    steps = soup.find_all("p", class_="recipe-step-description-content")
    stepsarray=[]
    for step in steps:
        s = step.text.strip()
        stepsarray.append(s)
    
    agent = {"User-Agent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36"}
    r = requests.get(url, headers = agent).text
    soup1 = BeautifulSoup(r, "html.parser")
    images = soup1.find_all("a", class_ = "recipe-step-cover ratio-container ratio-container-4-3 glightbox")
    imagesurlarray = []
    for image in images:
        i = image.attrs["href"]
        imagesurlarray.append(i)
                
    results2 = ""
    if len(stepsarray) == len(imagesurlarray):
        for i in range(len(stepsarray)):
            results2 += (stepsarray[i]+"@"+imagesurlarray[i] + "@")
    else:
        for i in range(len(stepsarray)):
            results2 += (stepsarray[i]+ "#")
    # print(results2)
    return results2[:len(results2)-1]


#servingamounts為幾人份
def get_peopleamounts():
    scraper2 = cfscrape.create_scraper()
    result2 = scraper2.get(url)
    soup = bs4.BeautifulSoup(result2.text, "html.parser")
    servingamounts = soup.find_all("div", class_="servings-info info-block")
    peopleamountsarray = []
    for servingamount in servingamounts:
        s = servingamount.text.strip()
        peopleamountsarray.append(s)
    
    results3 = ""
    for i in range(len(peopleamountsarray)):
        results3 += (peopleamountsarray[i]+ " , " )
    # print(results3)
    return results3[:len(results3)-3]

#servingamounts為花費總時間    
def get_time():
    scraper2 = cfscrape.create_scraper()
    result2 = scraper2.get(url)
    soup = bs4.BeautifulSoup(result2.text, "html.parser")
    times = soup.find_all("div", class_="time-info info-block")
    timearray = []
    for time in times:
        t = time.text.strip()
        timearray.append(t)
    
    results4 = ""
    for i in range(len(timearray)):
        results4 += (timearray[i]+ " , " )
    return results4[:len(results4)-3]
            
        
    
    
        
        
        
    