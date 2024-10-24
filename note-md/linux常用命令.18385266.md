## 进程

### 根据pid查询docker所属容器

```shell
cat /proc/<PID>/cgroup
```

### 根据pid查询端口占用

```shell
lsof -i -P -n | grep <PID>
```

## 性能

### 查看GPU占用

> 数字表示刷新时间，例如：每隔3秒刷新一次

```shell
nvidia-smi -l 3
```
