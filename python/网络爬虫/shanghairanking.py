import requests
from lxml import html


def getHtmlText(url):
    try:
        cert_path = './CA.cer'
        r = requests.get(url, timeout=60, verify=cert_path)
        r.encoding = 'utf-8'
        return r.text
    except:
        return "产生异常"


def parseHTML(url):
    # 访问网站
    text = getHtmlText(url)
    print(text)

    htmlStr = html.etree.HTML(text)
    # 获取每行的大学数据
    trset = htmlStr.xpath('//table[@class="rk-table"]//tbody//tr')
    for tr in trset:
        # 中文名
        nameCn = tr.xpath('.//a[@class="name-cn"]//text()')[0].strip()
        directUrl = 'https://www.shanghairanking.cn' + tr.xpath('.//a[@class="name-cn"]/@href')[0].strip()
        detailHtmlText = getHtmlText(directUrl)

        detailHtml = html.etree.HTML(detailHtmlText)
        scoreBox = detailHtml.xpath('//div[@class="score-box"][0]')
        print(scoreBox)

        # 英文名
        nameEn = tr.xpath('.//a[@class="name-en"]//text()')[0].strip()
        # 省份
        prov = tr.xpath('./td[3]//text()')[0].strip()
        # 类型
        type = tr.xpath('./td[4]//text()')[0].strip()
        # 总分
        totalScore = tr.xpath('./td[5]//text()')[0].strip()
        print('中文名:%s,英文名:%s,省份:%s,类型:%s,总分:%s' % (nameCn, nameEn, prov, type, totalScore))


if __name__ == "__main__":
    parseHTML("https://www.shanghairanking.cn/rankings/bcur/202411")
