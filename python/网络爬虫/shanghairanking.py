import requests
from lxml import html

from selenium.webdriver.chrome.options import Options
from selenium import webdriver
from time import sleep


def getHtmlText(url, headers):
    try:
        cert_path = './CA.cer'
        r = requests.get(url, timeout=60, verify=cert_path, headers=headers)
        r.encoding = 'utf-8'
        return r.text
    except:
        return "产生异常"


def parseHTML(url, detailUrl):
    # 访问网站
    text = getHtmlText(url, {})
    # print(text)
    htmlStr = html.etree.HTML(text)
    # 获取每行的大学数据
    trset = htmlStr.xpath('//table[@class="rk-table"]//tbody//tr')
    for tr in trset:
        # 中文名
        name_cn = tr.xpath('.//a[@class="name-cn"]//text()')[0].strip()
        # 英文名
        name_en = tr.xpath('.//a[@class="name-en"]//text()')[0].strip()

        detail_html_str = webHtml(detailUrl + tr.xpath('.//a[@class="name-en"]//@href')[0].strip())
        detail_html = html.etree.HTML(detail_html_str)
        score_box_html = detail_html.xpath('//div[@class="score-box"]')[0]
        score_set = score_box_html.xpath('.//span[@class="score"]//text()')
        score_0 = score_set[0]
        score_1 = score_set[1]
        score_2 = score_set[2]
        score_3 = score_set[3]
        score_4 = score_set[4]
        score_5 = score_set[5]
        # 省份
        prov = tr.xpath('./td[3]//text()')[0].strip()
        # 类型
        type = tr.xpath('./td[4]//text()')[0].strip()
        # 总分
        total_score = tr.xpath('./td[5]//text()')[0].strip()
        print('中文名:%s,英文名:%s,省份:%s,类型:%s,总分:%s,教师投入:%s,学习风气:%s,硬件设施:%s,奖助学金:%s,校园生活:%s,就业发展:%s,' % (
        name_cn, name_en, prov, type, total_score,
            score_0, score_1, score_2, score_3, score_4, score_5))


def webHtml(url):
    # 通过get(url)
    browser.get(url)
    sleep(1)
    return browser.page_source


if __name__ == "__main__":
    global browser

    # google
    chrome_options = Options()
    chrome_options.add_argument('--headless')
    chrome_options.add_argument('--disable-gpu')
    browser = webdriver.Chrome(options=chrome_options)
    parseHTML("https://www.shanghairanking.cn/rankings/bcur/202411", "https://www.shanghairanking.cn")
    print(browser.page_source)
    browser.quit()
