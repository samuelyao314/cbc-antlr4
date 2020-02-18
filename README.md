# cbc-antlr4
[cbc](https://github.com/aamine/cbc) 早些的代码在这里，对应的中文书籍是 《自制编译器》。 这是一本很棒的书。
但从使用的角度看，antlr4 现在比 JAVACC 使用上更广泛，而且更容易。 

本项目做以下的修改

* 词法分析，语法分析的自动生成，从 JavaCC  改成 antlr4
* 之前抽象语法树的生成， 是写在 JavaCC 文件里的，这种方式是非常不好的。基于antlr4生成的解析器，独立实现抽象语法树
* TODO
