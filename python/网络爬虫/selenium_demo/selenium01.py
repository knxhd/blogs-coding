from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from time import sleep


def web(url):
    # google
    chrome_options = Options()
    chrome_options.add_argument('--headless')
    chrome_options.add_argument('--disable-gpu')
    browser = webdriver.Chrome(options=chrome_options)
    # browser.implicitly_wait(10)
    # 通过get(url)
    browser.get(url)
    sleep(2)
    print(browser.page_source)
    browser.quit()


if __name__ == '__main__':
    web("https://www.shanghairanking.cn/institution/tsinghua-university")
