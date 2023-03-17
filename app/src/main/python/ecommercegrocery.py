import cfscrape
import bs4
import csv
from urllib.request import urlopen
from bs4 import BeautifulSoup
import re
#總共三個電商平台，每個電商平台有分別四個method(標題，價錢，產品url,照片url)，所以總共有12個method
#第一家電商平台：鮮拾
#鮮拾的產品標題
# ingredients = input("請輸入要購買的食材：")
ingredients = ""

def input_ingredients(igd):
    global ingredients
    ingredients = igd
    # return ingredients   

def get_firststore_titles():
    site = "https://www.pickup.com.tw/products?query="+ingredients
    scraper1 = cfscrape.create_scraper()
    result1 = scraper1.get(site)
    soup = bs4.BeautifulSoup(result1.text, "html.parser")
    titles = soup.find_all("div", class_="title text-primary-color")
    titlesarray=[]
    for title in titles:
        titlesarray.append(title.text.strip())
    return titlesarray
        
    #鮮拾的產品價錢   
def get_firststore_prices():
    site = "https://www.pickup.com.tw/products?query="+ingredients
    scraper1 = cfscrape.create_scraper()
    result1 = scraper1.get(site)
    soup = bs4.BeautifulSoup(result1.text, "html.parser")
    prices = soup.find_all("div", class_="price-sale price sl-price primary-color-price")
    pricesarray=[]
    for price in prices:
        pricesarray.append(price.text.strip())
    # print(pricesarray)
    return pricesarray
    #鮮拾的產品url    
def get_firststore_websiteurls():
    site = "https://www.pickup.com.tw/products?query="+ingredients
    scraper1 = cfscrape.create_scraper()
    result1 = scraper1.get(site)
    soup = bs4.BeautifulSoup(result1.text, "html.parser")
    urls = soup.find_all("product-list-variation-selector", class_ = "product-list-variant-selector-container")
    urlproducts = []
    for url in urls:
        urlproducts.append(url.attrs["product_url"])
    # print(urlproducts)
    return urlproducts
    #鮮拾的產品照片url   
def get_firststore_imagesurls():  
    site = "https://www.pickup.com.tw/products?query="+ingredients
    scraper1 = cfscrape.create_scraper()
    result1 = scraper1.get(site)
    soup = bs4.BeautifulSoup(result1.text, "html.parser")
    images = soup.find_all("div", class_ = "boxify-image js-boxify-image center-contain sl-lazy-image")
    imageslinks = []
    for image in images:
        i = image.attrs["style"].replace('background-image:url','')
        imageslinks.append(i)
    # print(imageslinks)
    return imageslinks
    #第二家電商平台：SUPERBUY市集
    #SUPERBUY市集的產品標題
def get_secondstore_titles():
    site = "https://www.superbuy.com.tw/search_result.php?tosearch=search&rank=9999&type=product&q=&keyword="+ingredients
    scraper1 = cfscrape.create_scraper()
    result1 = scraper1.get(site)
    soup = bs4.BeautifulSoup(result1.text, "html.parser")
    twentyfourhoursections = soup.find_all("div", class_="flag_24h")
    twentyfourarray=[]
    for twentyfoursection in twentyfourhoursections:
        twentyfourarray.append(twentyfoursection.text.strip())
    titles1 = soup.find_all("li", class_="label")
    title1array=[]
    for title1 in titles1:
        title1array.append(title1.text.strip())
    for i in range(len(twentyfourarray)):
        title1array.pop(0)
    titles2 = soup.find_all("li", class_="small")
    title2array=[]
    for title2 in titles2:
        title2array.append(title2.text.strip())
    for i in range(len(twentyfourarray)):
        title2array.pop(0)
    titlearray = []
    # for i in range(len(title1array)):
    #     titlearray.append(str(title1array[i]+title2array[i]))
    return title1array

    #SUPERBUY市集的產品價錢   
def get_secondstore_prices():
    site = "https://www.superbuy.com.tw/search_result.php?tosearch=search&rank=9999&type=product&q=&keyword="+ingredients
    scraper1 = cfscrape.create_scraper()
    result1 = scraper1.get(site)
    soup = bs4.BeautifulSoup(result1.text, "html.parser")
    twentyfourhoursections = soup.find_all("div", class_="flag_24h")
    twentyfourarray=[]
    for twentyfoursection in twentyfourhoursections:
        twentyfourarray.append(twentyfoursection.text.strip())
    def substring_after(s, delim):
        return s.partition(delim)[2]
    prices= soup.find_all("div", class_="product col-xs-6 col-sm-6 col-md-4")
    pricesarray=[]
    for price in prices:
        p = price.text.strip()
        p = re.sub("[^$^0-9]", "", p)
        p= substring_after(p, "$")
        p = "$" + p
        pricesarray.append(p)
    for i in range(len(twentyfourarray)):
        pricesarray.pop(0)
    # print(pricesarray)
    return pricesarray
    #SUPERBUY市集的產品url    
def get_secondstore_websiteurls():
    site = "https://www.superbuy.com.tw/search_result.php?tosearch=search&rank=9999&type=product&q=&keyword="+ingredients
    scraper1 = cfscrape.create_scraper()
    result1 = scraper1.get(site)
    soup = bs4.BeautifulSoup(result1.text, "html.parser")
    twentyfourhoursections = soup.find_all("div", class_="flag_24h")
    twentyfourarray=[]
    for twentyfoursection in twentyfourhoursections:
        twentyfourarray.append(twentyfoursection.text.strip())   
    urls= soup.find_all("div", class_="product col-xs-6 col-sm-6 col-md-4")
    websitesurl = []
    for url in urls:
        i = url.find("a")["href"]
        w = "https://www.superbuy.com.tw/"+i
        websitesurl.append(w)
    for i in range(len(twentyfourarray)):
        websitesurl.pop(0)
    # print(websitesurl)
    return websitesurl
    #SUPERBUY市集的產品照片url   
def get_secondstore_imagesurls():
    site = "https://www.superbuy.com.tw/search_result.php?tosearch=search&rank=9999&type=product&q=&keyword="+ingredients
    scraper1 = cfscrape.create_scraper()
    result1 = scraper1.get(site)
    soup = bs4.BeautifulSoup(result1.text, "html.parser")
    twentyfourhoursections = soup.find_all("div", class_="flag_24h")
    twentyfourarray=[]
    for twentyfoursection in twentyfourhoursections:
        twentyfourarray.append(twentyfoursection.text.strip())
    images = soup.find_all("img", class_ = "img-responsive lazyload")
    imagesurl = []
    for image in images:
        i = image.attrs["src"]
        imagesurl.append(i)
    for i in range(len(twentyfourarray)):
        imagesurl.pop(0)
    # print(imagesurl)
    return imagesurl
    
    #第三家電商平台：台畜
    #台畜的產品標題
def get_thirdstore_titles():
    site = "https://shop.tham.com.tw/products?query="+ingredients
    scraper1 = cfscrape.create_scraper()
    result1 = scraper1.get(site)
    soup = bs4.BeautifulSoup(result1.text, "html.parser")
    titles = soup.find_all("div", class_="title text-primary-color title-container ellipsis force-text-align-")
    titlesarray=[]
    for title in titles:
        titlesarray.append(title.text.strip())
    # print(titlesarray)
    return titlesarray
#台畜的產品價錢   
def get_thirdstore_prices():
    site = "https://shop.tham.com.tw/products?query="+ingredients
    scraper1 = cfscrape.create_scraper()
    result1 = scraper1.get(site)
    soup = bs4.BeautifulSoup(result1.text, "html.parser")
    prices = soup.find_all("div", class_="price-sale price force-text-align-")
    pricesarray=[]
    for price in prices:
        pricesarray.append(price.text.strip())
    # print(pricesarray)
    return pricesarray
#台畜的產品url    
def get_thirdstore_websiteurls():
    site = "https://shop.tham.com.tw/products?query="+ingredients
    scraper1 = cfscrape.create_scraper()
    result1 = scraper1.get(site)
    soup = bs4.BeautifulSoup(result1.text, "html.parser")
    urls = soup.find_all("li", class_="boxify-item product-item")
    urlsarray = []
    for url in urls:
        i = url.find("a")["href"]
        i = "https://shop.tham.com.tw/"+i
        urlsarray.append(i)
    # print(urlsarray)
    return urlsarray
#台畜的產品照片url   
def get_thirdstore_imagesurls():
    site = "https://shop.tham.com.tw/products?query="+ingredients
    scraper1 = cfscrape.create_scraper()
    result1 = scraper1.get(site)
    soup = bs4.BeautifulSoup(result1.text, "html.parser")
    images = soup.find_all("div", class_ = "boxify-image center-contain sl-lazy-image")
    imageslinks = []
    for image in images:
        i = image.attrs["style"].replace("background-image:url(","").replace(")","")
        imageslinks.append(i)
    # print(imageslinks)
    return imageslinks
    

def get_firststore():
    title = get_firststore_titles()
    price = get_firststore_prices()
    websiteurl = get_firststore_websiteurls()
    imagesurl = get_firststore_imagesurls()
    info = ""
    # if len(title) == len(price) == len(websiteurl) == len(imagesurl):
    #     for i in range(len(title)):
    #         info += title[i] + "," + price[i] + "," + websiteurl[i] + "," + imagesurl[i] + "," 
    for i in range(len(title)):
        info += title[i] + "," + price[i] + "," + websiteurl[i] + "," 
        return info
    

def get_secondstore():
    title = get_secondstore_titles()
    price = get_secondstore_prices()
    websiteurl = get_secondstore_websiteurls()
    imagesurl = get_secondstore_imagesurls()
    info = ""
    # if len(title) == len(price) == len(websiteurl) == len(imagesurl):
    #     for i in range(len(title)):
    #         info += title[i] + "," + price[i] + "," + websiteurl[i] + "," + imagesurl[i] + "," 
    for i in range(len(title)):
        info += title[i] + "," + price[i] + "," + websiteurl[i] + "," 

        return info

def get_thirdstore():
    title = get_thirdstore_titles()
    price = get_thirdstore_prices()
    websiteurl = get_thirdstore_websiteurls()
    imagesurl = get_thirdstore_imagesurls()
    info = ""
    # if len(title) == len(price) == len(websiteurl) == len(imagesurl):
    #     for i in range(len(title)):
    #         info += title[i] + "," + price[i] + "," + websiteurl[i] + "," + imagesurl[i] + "," 
    for i in range(len(title)):
        info += title[i] + "," + price[i] + "," + websiteurl[i] + "," 

        return info[:len(info)-1]

def get_all():
    # firststore = secondstore = thirdstore = ""
    # if get_firststore() != None:
    #     firststore = get_firststore()
    # if get_secondstore() != None:
    #     secondstore = get_secondstore()
    # if get_thirdstore() != None:
    #     thirdstore = get_thirdstore()
    # info = firststore + secondstore + thirdstore
    info = get_firststore() + get_secondstore() + get_thirdstore()
    # info = get_firststore() 
    return info