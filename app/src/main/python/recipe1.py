import cfscrape, bs4, csv
from urllib.request import urlopen
from bs4 import BeautifulSoup
#依據搜尋食材取得相對應食譜資訊：urls為食譜相對應連結，titles為食譜的名稱，clickimages為食譜的封面照片

ingredients = "蘋果"

def input_ingredients(idg):
    global ingredients
    ingredients = idg
    
def get_recipes():
    site = "https://icook.tw/search/食材：" + ingredients+"/"
    scraper2 = cfscrape.create_scraper()
    result2 = scraper2.get(site)
    soup1 = bs4.BeautifulSoup(result2.text, "html.parser")

    urls = soup1.find_all("a", class_ ="browse-recipe-link")
    urlsarray=[]
    for url in urls:
        i = url.get("href")
        i="https://icook.tw/" + i 
        urlsarray.append(i)

    titles = soup1.find_all("h2", class_="browse-recipe-name")
    titlesarray=[]
    for title in titles:
        i = title.text.strip()
        titlesarray.append(i)

    clickimages = soup1.find_all("img", class_="browse-recipe-cover-img lazyload")
    clickimagesarray = []
    for clickimage in clickimages:
        i = clickimage.get("data-src")
        clickimagesarray.append(i)
    
    results = ""
    for i in range(len(titlesarray)):
        # results += (titlesarray[i]+" , "+clickimagesarray[i]+" , "+titlesarray[i])
        results += (titlesarray[i]+","+clickimagesarray[i] + "," + urlsarray[i] + ",")
        # results += (titlesarray[i]+", ")
        # results += (clickimagesarray[i]+", ")
    # print(results)
    return results[:len(results)-1]
    # return results
    # return titlesarray
        
        