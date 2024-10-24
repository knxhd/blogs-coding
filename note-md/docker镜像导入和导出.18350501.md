## save和export

> - 镜像导入和导出有2种方式，分别为 save和load、export和import
> - save导出的是镜像；export导出的为容器
> - save导出会保存镜像所有的提交记录；export不会保存镜像的历史记录
> - load用来载入镜像包；import用来载入容器包，但两者都会恢复为镜像
> - load不能对载入的镜像重命名；而import可以为镜像指定新名称

### 基本命令

#### save和load

1. 导出镜像

```shell
docker save -o output_file image_name
或
docker save > output_file image_name
```

> - output_file：文件格式为tar
> - image_name：镜像名称

2. 导入镜像文件

```shell
docker load -i output_file
或
docker load < output_file
```

> - output_file：导出的镜像文件

#### export和import

1. 导出容器

```shell
docker export -o output_file container_name
或
docker export container_name > output_file
```

> - output_file：导出的镜像文件，格式为tar
> - container_name：容器名称

2. 导入镜像文件

```shell
docker import output_file new_image_name:tag
或
cat output_file | docker import - new_image_name:tag
```

> - output_file：导出的镜像文件，格式为tar
> - new_image: 新的镜像名称，tag：镜像标签
