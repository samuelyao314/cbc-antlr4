# cbc-antlr4
[cbc](https://github.com/aamine/cbc) 早些的代码在这里，对应的中文书籍是 《自制编译器》。 这是一本很棒的书。
但从使用的角度看，antlr4 现在比 JAVACC 使用上更广泛，更简便。 

本项目做以下的修改

* 词法分析，语法分析的自动生成，从 JavaCC  改成 antlr4
* 之前抽象语法树的生成， 是写在 JavaCC 文件里的，这种方式是非常不好的。会独立到出来
* TODO

# antlr4
按照[官方文档](https://github.com/antlr/antlr4/blob/master/doc/getting-started.md)安装后,  就会有 antlr4 命令。

手动生成解析程序，就在 shell 下执行以下命令

```shell script
$ antlr4 -o src/main/java//net/loveruby/cflat/parser/gen -package net.loveruby.cflat.parser.gen -listener -visitor -lib src/main src/main/Cbc.g4

```

如果是用了IDEA， 建议用 [插件](https://plugins.jetbrains.com/plugin/7358-antlr-v4-grammar-plugin/)。