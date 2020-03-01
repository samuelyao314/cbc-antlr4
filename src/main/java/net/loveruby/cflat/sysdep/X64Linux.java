package net.loveruby.cflat.sysdep;

import net.loveruby.cflat.asm.Type;
import net.loveruby.cflat.type.TypeTable;
import net.loveruby.cflat.utils.ErrorHandler;

// X86-64 架构下的 linux 平台
public class X64Linux implements Platform {

    public TypeTable typeTable() {
        return TypeTable.ilp64();
    }

    public CodeGenerator codeGenerator(CodeGeneratorOptions opts, ErrorHandler h) {
        return new net.loveruby.cflat.sysdep.x64.CodeGenerator(opts,  h);
    }


//    public Assembler assembler(ErrorHandler h) {
//        return new GNUAssembler(h);
//    }
//
//    public Linker linker(ErrorHandler h) {
//        return new GNULinker(h);
//    }
}
