## pip的使用

### 升级pip

```shell
python3 -m pip install --upgrade pip
```

### 镜像源设置

1. 查看镜像源 `pip config list`
2. 指定镜像源更新依赖 `pip3 install numpy -i https://pypi.tuna.tsinghua.edu.cn/simple`
3. 设置全局镜像源：

   ```shell
   pip config set global.index-url mirror_url_path  #指定镜像服务器域名地址
   pip config set global.timeout number #设置连接超时时间，以秒为单位，指定number秒后，连接超时 
   pip config set install.trusted-host hostname #将以上镜像服务器的域名加入到Install命令的信任域名列表内
   ```

   - 例如：

   ```shell
   pip config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple
   ```

### 常用镜像源

```tex
#国内比较常用的镜像地址，本人选择的是阿里云的镜像服务
http://pypi.douban.com/simple/ #豆瓣
https://pypi.tuna.tsinghua.edu.cn/simple  #清华开源
https://mirrors.aliyun.com/pypi/simple/  #阿里云，比较靠谱，最起码可以安装jupyter
https://pypi.mirrors.ustc.edu.cn/simple/  #中科大
https://pypi.hustunique.com/ #华中理工
https://pypi.sdutlinux.org/  #山东理工
```

### requirements.txt

> requirements.txt 是 python 在不同的环境中对依赖包的一种约定，用于列出 Python 项目中所有的依赖包以及对应版本号的文本文件。一般在项目的在工程目录下。

#### 编写格式

```tex
pkg==version 等于版本
pkg>version 大于版本
pkg<version 小于版本
pkg>=version 大于等于版本
pkg<=version 小于等于版本
pkg~=version 大于版本
pkg >= 1.0, <=2.0 容版本，使用任何大于或等于指定版本，但不大于当前发行系列的版本，
```

#### 安装 `requirements.txt`中的包

```shell
pip install -r requirements.txt
```

#### 更新 `requirements.txt`中的包

```shell
pip freeze > requirements.txt
```

## 日志配置

- processName：表示进程号，理论每个请求的进程号是不一样的，可以在请求入口设置，以此进行链路追踪。设置方式：

  ```python
      import multiprocessing
      # 设置进程号，作为日志记录的前缀，方便做链路追踪
      multiprocessing.current_process().name = data.userid
  ```
- 完整示例：

```python-repl
import logging
from logging.handlers import TimedRotatingFileHandler
import os

logger = logging.getLogger('film_grain')
logger.setLevel(logging.DEBUG)

# 日志文件根目录
PARENT_DIR = os.path.split(os.path.realpath(__file__))[0]

# 日志目录
LOGGING_DIR = "/home/logs/"
if not os.path.exists(LOGGING_DIR):
    os.mkdir(LOGGING_DIR)

handlers = {
    "info": TimedRotatingFileHandler(filename = LOGGING_DIR + "info.log", when = 'D', interval = 1, backupCount = 30),
    "error": TimedRotatingFileHandler(filename = LOGGING_DIR + "error.log", when = 'D', interval = 1, backupCount = 30)
}

formatter = logging.Formatter("%(asctime)s [%(levelname)s] [%(processName)s] %(funcName)s - %(message)s")

# 设置每个处理器的日志级别
for level, handler in handlers.items():
    handler.setLevel(getattr(logging, level.upper()))
    handler.setFormatter(formatter)
    logger.addHandler(handler)

# 如果你想在控制台也看到日志
console = logging.StreamHandler()
console.setLevel(logging.DEBUG)
console.setFormatter(formatter)
logger.addHandler(console)
```
