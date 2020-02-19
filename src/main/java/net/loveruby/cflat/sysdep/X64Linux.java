package net.loveruby.cflat.sysdep;

import net.loveruby.cflat.type.TypeTable;

// X86_64 架构下的 linux 平台
public class X64Linux implements Platform {

    @Override
    public TypeTable typeTable() {
        return TypeTable.ilp64();
    }
}
