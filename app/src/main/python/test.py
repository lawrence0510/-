import recipe1
import recipe2

i = input("請輸入食材：")
recipe1.input_ingredients(i)
print(recipe1.get_recipes())

while(True):
    l = input("請輸入食譜網址：")
    if l == "exit":
        break
    recipe2.input_url(l)
    print(recipe2.get_ingredientsandamounts())
    print(recipe2.get_stepsandimagesurls())
    print(recipe2.get_peopleamounts())
    print(recipe2.get_time())