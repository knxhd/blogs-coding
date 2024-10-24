> - 数据导入和导出依赖于命令 `elasticdump`

## 数据导出

```powershell
#!/bin/bash
ES=http://ip:port

ED=数据保存位置

datename=$(date +%Y-%m-%d)
#datename=2021-08-20

index=导出的索引名

echo "elasticdump --input=$ES/$index --output=$ED/$index.json"
    elasticdump --input=$ES/$index --output=${ED}/${index}_setting.json  --type=settings  --limit=10000
    elasticdump --input=$ES/$index --output=${ED}/${index}_analyzer.json --type=analyzer  --limit=10000
  # elasticdump --input=$ES/$index --output=${ED}/${index}_alias.json  --type=alias  --limit=10000
  # elasticdump --input=$ES/$index --output=${ED}/${index}_template.json  --type=template  --limit=10000
    elasticdump --input=$ES/$index --output=${ED}/${index}_mapping.json  --type=mapping   --limit=10000
    elasticdump --input=$ES/$index --output=${ED}/${index}_data.json --type=data  --limit=10000

cd $ED
#tar -zcvf  $index.tar.gz $index.json
#find $ED/* -type f -mtime +10 -exec rm {} \;

echo "success"

```
