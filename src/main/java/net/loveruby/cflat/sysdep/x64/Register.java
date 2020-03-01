package net.loveruby.cflat.sysdep.x64;
import net.loveruby.cflat.asm.Type;
import net.loveruby.cflat.asm.SymbolTable;

class Register extends net.loveruby.cflat.asm.Register {
    RegisterClass _class;
    Type type;

    Register(RegisterClass _class, Type type) {
        this._class = _class;
        this.type = type;
    }

    Register forType(Type t) {
        return new Register(_class, t);
    }

    public boolean isRegister() { return true; }

    public boolean equals(Object other) {
        return (other instanceof Register) && equals((Register)other);
    }

    /** size difference does NOT matter. */
    public boolean equals(Register reg) {
        return _class.equals(reg._class);
    }

    public int hashCode() {
        return _class.hashCode();
    }

    RegisterClass registerClass() {
        return _class;
    }

    String baseName() {
        return _class.toString().toLowerCase();
    }

    public String toSource(SymbolTable table) {
        // GNU assembler dependent
        return "%" + typedName();
    }

    private String typedName() {
        switch (type) {
        case INT8: return lowerByteRegister();
        case INT16: return wordRegister();
        case INT32: return doubleWordRegister();
        case INT64: return quadWordRegister();
        default:
            throw new Error("unknown register Type: " + type);
        }
    }

    private String quadWordRegister() {
        switch (_class) {
            case AX:
            case BX:
            case CX:
            case DX:
            case SI:
            case DI:
            case BP:
            case SP:
                return "r" + baseName();

            case R8:
            case R9:
            case R10:
            case R11:
            case R12:
            case R13:
            case R14:
            case R15:
                return baseName();

            default:
                throw new Error("does not have lower-byte register: " + _class);
        }
    }

    private String doubleWordRegister() {
        switch (_class) {
            case AX:
            case BX:
            case CX:
            case DX:
            case SI:
            case DI:
            case BP:
            case SP:
                return "e" + baseName();

            case R8:
            case R9:
            case R10:
            case R11:
            case R12:
            case R13:
            case R14:
            case R15:
                return baseName() + "d";

            default:
                throw new Error("does not have lower-byte register: " + _class);
        }
    }

    private String wordRegister() {
        switch (_class) {
            case AX:
            case BX:
            case CX:
            case DX:
            case SI:
            case DI:
            case BP:
            case SP:
                return baseName();

            case R8:
            case R9:
            case R10:
            case R11:
            case R12:
            case R13:
            case R14:
            case R15:
                return baseName() + "w";

            default:
                throw new Error("does not have lower-byte register: " + _class);
        }
    }

    private String lowerByteRegister() {
        switch (_class) {
            case AX:
            case BX:
            case CX:
            case DX:
                return baseName().substring(0, 1) + "l";

            case SI:
            case DI:
            case BP:
            case SP:
                return baseName() + "l";

            case R8:
            case R9:
            case R10:
            case R11:
            case R12:
            case R13:
            case R14:
            case R15:
                return baseName() + "b";

            default:
                throw new Error("does not have lower-byte register: " + _class);
        }
    }

    public String dump() {
        return "(Register " + _class.toString() + " " + type.toString() + ")";
    }
}
