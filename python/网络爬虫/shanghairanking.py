import requests
from lxml import etree

def getHtmlText(url):
    try:
        cert_path = '/Users/tianluhua/personal/coding/blogs-coding/python/网络爬虫/CA.cer'
        r = requests.get(url, timeout=60, verify=cert_path)
        r.encoding='utf-8'
        return r.text
    except:
        return "产生异常"

def parseHTML(url):
    # 访问网站
    text = getHtmlText(url)
    print(text)

    html = etree.html(text)
    trset = html.xpath('//div[@class="rk-table"]/tbody//tr')
    print(trset)


if __name__ == "__main__":
    parseHTML("https://www.shanghairanking.cn/rankings/bcur/202411")