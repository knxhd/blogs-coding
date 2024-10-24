## word基础

### docx和doc的区别

> - doc是微软特有的一种文件格式，其本质是一个二进制的文件
> - docx是基于XML的开放文档格式，是Office Open Xml的一部分。

### docx组成部分

![1729681300569](https://img2023.cnblogs.com/blog/2855961/202410/2855961-20241023193723958-313175001.png)

![1729681504370](https://img2023.cnblogs.com/blog/2855961/202410/2855961-20241023193724306-207450299.png)

- 一个完整的docx文档由4部分构成。即 `_rels`、`docProps`、`word`和 `[Content_Types].xml`
- `word`文件夹定义了文档的内容和格式等

  - `document.xml`：存储文档的主要内容。
  - `styles.xml`：定义文档的样式。
  - `settings.xml`：包含文档的配置信息。
  - `fontTable.xml`：列出文档中使用的字体。
  - `webSettings.xml`：包含与网页相关的设置。
- `_rels` 文件夹

  - `relationships` 文件，定义文档中各部分的关系。
- `docProps`

  * `core.xml`：包含文档的核心属性，如作者、标题等。
  * `app.xml`：包含应用程序特定的信息。
- **`[Content_Types].xml`**： 定义文档中各部分的内容类型。

### word-XML规范

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<w:document xmlns:wpc="http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas"
    xmlns:cx="http://schemas.microsoft.com/office/drawing/2014/chartex"
    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
    xmlns:o="urn:schemas-microsoft-com:office:office"
    xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
    xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
    xmlns:v="urn:schemas-microsoft-com:vml"
    xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing"
    xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing"
    xmlns:w10="urn:schemas-microsoft-com:office:word"
    xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
    xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
    xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml"
    xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex"
    xmlns:wpg="http://schemas.microsoft.com/office/word/2010/wordprocessingGroup"
    xmlns:wpi="http://schemas.microsoft.com/office/word/2010/wordprocessingInk"
    xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml"
    xmlns:wps="http://schemas.microsoft.com/office/word/2010/wordprocessingShape"
    mc:Ignorable="w14 w15 w16se wp14">
    <w:body>
        <w:p>
            <w:pPr>
                <w:snapToGrid w:val="0"/>
                <w:jc w:val="center"/>
                <w:rPr>
                    <w:rFonts w:ascii="Times New Roman" w:eastAsia="宋体" w:hAnsi="Times New Roman"/>
                    <w:sz w:val="28"/>
                    <w:szCs w:val="28"/>
                </w:rPr>
            </w:pPr>
            <w:r>
                <w:rPr>
                    <w:rFonts w:ascii="Times New Roman" w:eastAsia="宋体" w:hAnsi="Times New Roman"/>
                    <w:sz w:val="28"/>
                    <w:szCs w:val="28"/>
                </w:rPr>
                <w:t>
                    测试
                </w:t>
            </w:r>
        </w:p>
    </w:body>
</w:document>
```

#### 基础标签

##### `<w:document>`

- 表示 Word 文档的根元素。

##### `<w:body>`

- 文档的主题部分

##### `<w:p>`

- 表示一个段落

###### `<w:pPr>`

- 表示应用到整个段落中的样式
- 标签内可添加对应的样式标签，用于定义整个段落的样式

##### `<w:r>`

- 表示一个文本运行（文本片段）

###### ` <w:rPr>`

- 表示一个文本片段的样式
- 标签内可添加对应的样式标签，用于定义文本片段的样式

###### `<w:t>`

- 表示一个文本片段中的内容
#### 样式标签

>样式优先级：`文本片段样式>段落样式`

##### 字体

- 标签：`<w:rFonts>`

- 属性：
  1. w:ascii：英文字体
  2. w:eastAsia：中文字体
  3. w:hAnsi：英文字体

```xml
<w:rFonts w:ascii="Times New Roman" w:eastAsia="宋体" w:hAnsi="Times New Roman"/>
```

##### 粗体

- 标签：<w:b />
- 无属性

```xml
<w:b />
```

##### 字体

- `<w:sz w:val="28"/>`：指定文本的字体大小，单位是半磅（1 磅 = 2 半磅），所以 `28` 表示 14 磅的字体大小。

- `<w:szCs w:val="28"/>`：指定复杂脚本文本（如阿拉伯文、希伯来文等）的字体大小，单位同样是半磅。

  - 初号：42 磅

  - 小初：36 磅

  - 一号：26 磅

  - 小一：24 磅

  - 二号：22 磅

  - 小二：18 磅

  - 三号：16 磅

  - 小三：15 磅

  - 四号：12 磅

  - 小四：10.5 磅

  - 五号：9 磅

  - 小五：7.5 磅

  - 六号：6.5 磅

  - 小六：6 磅

  - 七号：5 磅

  - 八号：5 磅


## 编码部分
