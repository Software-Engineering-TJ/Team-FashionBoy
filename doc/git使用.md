# 使用git实现版本控制

## 前置知识

git是命令行式的版本控制系统。所谓版本控制，可以理解为本地仓库代码的更新、回退，远程仓库的托管等。在个人电脑任意文件路径下右键鼠标，选择打开git bash here，就打开了在此路径下的git控制台，你将进行的所有操作都会在此路径下进行。

如果你下载Git后，打开文件夹，可发现内部文件结构与linux操作系统很相似。因此，我们可以在git控制台内，通过纯命令行方式实现添加、删除、查看（vim）文件等基本功能。

使用git必须了解以下概念：工作区，暂存区（缓冲区），本地仓库和远程仓库。

使用git clone命令克隆远程仓库已有项目，拷贝到本地变成**工作区**，可以随意修改。工作区能且仅能使用git add命令将文件上传至**暂存区**，暂存区可以看成上传仓库的一个中间桥梁。对于已上传到暂存区的文件，使用git commit将文件上传至**本地仓库**。对于已上传至本地仓库的文件，使用git push命令将文件重新上传至**远程仓库**（github网页中显示），实现更新。

以上是最简单的版本控制实例。下面按功能划分，详解git命令。

## 创建本地工作区

| 命令                 | 说明                                         |
| -------------------- | -------------------------------------------- |
| git init             | 将此路径下的文件生成为工作区（添加.git）     |
| git clone [url或ssh] | 克隆远程仓库的项目，导入到此路径下作为工作区 |

## 提交与修改

| 命令       | 说明                                                         |
| ---------- | ------------------------------------------------------------ |
| git add    | 需要后续参数（“.”或指定文件名，“.”表示所有文件，注意与add用空格划分），添加到暂存区。 |
| git status | 此命令将遍历你的项目文件（包括内容），指出你的项目文件与本地仓库的区别。如果新建或修改文件而未add，以红色字体显示信息；如果add过，以绿色字体显示待commit的文件。 |
| git diff   | 比较工作区和暂存区的差异。（网上有说法称这是比较暂存区和本地仓库，存疑，可以自行测试） |
| git commit | 需要后续参数（-m “注释信息”或[文件名]），添加到本地仓库。    |
| git reset  | 需要后续参数HEAD，用于回退版本。可以把不同分支和分支内不同版本想象成从上到下延申的树，用HEAD指针指向当前分支的当前版本。HEAD表示当前版本，HEAD^表示上一个版本，HEAD~n表示上n个版本。 |
| git log    | 查看提交日志。                                               |
| 其他       | 删除工作区文件、移动或重命名等。                             |

## 远程操作

| 命令       | 说明                                                         |
| ---------- | ------------------------------------------------------------ |
| git remote | 一般用法为：git remote add [shortname] [url或ssh]，与一个远程仓库建立连接。[shortname]在我看来是后面url的缩写，一般写为origin。 |
| git push   | 一般用法为：git push [远程主机名] [本地分支名]:[远程分支名]，将本地的分支上传到远程并合并。[远程主机名]为origin，[本地分支名]和[远程分支名]并无直接关系，他们分别存在于本地和远程仓库，不过他俩要是同名，可以省略冒号后的内容。 |
| git fetch  | 从远程获取代码库。                                           |
| git pull   | 下载远程代码并合并，相当于fetch+merge。                      |

## 使用实例

### 1.在本地已经创建好项目，想直接上传至github

```
git init
git remote add origin ....
git add .
(git status查看状态，可选)
git commit -m "..."
git push origin main:main
```

### 2.别人已经建好github仓库，想直接克隆到本地更改，再上传

```
git clone ....
（更改）
git add .
(git status查看状态，可选)
git commit -m "..."
git push origin main:main
```

### 3.查看目前连接的是哪个远程仓库

```
git remote -v
```

### 4.别人对github进行了更新，想同步自己本地的版本

```
git pull origin main:main
```
除非保证新加入的代码可运行且不会与主分支发生冲突，否则请建立您自己的分支，进行编程与调试！
