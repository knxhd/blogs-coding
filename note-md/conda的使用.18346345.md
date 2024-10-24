## conda安装

### linux


## 基本命令

### 创建环境

```shell
conda create [可选项] env_name python=3.7
# 可选项 -c 镜像地址，采用镜像地址创建环境
# 例如：conda create -c https://conda.anaconda.org/conda-forge -n python37 python=3.7
```

### 激活环境

1. 第一次采用 `source activate env_name`

### 查看有哪些环境

```shell
conda env list
```

### 退出环境

```shell
conda deactivate
```

### 删除环境

```shell
conda remove -n xxx --all
```

### 关闭自动激活的base环境：

```shell
conda config --set auto_activate_base false

## 同理，开启命令为
conda config --set auto_activate_base true
```

### 删除环境

```shell
conda remove --name env_name --all
```

## 安装依赖

### 安装OCC

```shell
conda install -c conda-forge pythonocc-core=7.7.2
```
