package org.jenkinsci.plugins.valgrind.model;

public enum ValgrindErrorKind {
    // Memcheck:
    InvalidRead,
    InvalidWrite,
    Leak_DefinitelyLost,
    Leak_PossiblyLost,
    Leak_StillReachable,
    Leak_IndirectlyLost,
    UninitCondition,
    UninitValue,
    Overlap,
    SyscallParam,
    InvalidFree,
    MismatchedFree,

    // Helgrind:
    Race,
    UnlockUnlocked,
    UnlockForeign,
    UnlockBogus,
    PthAPIerror,
    LockOrder,
    Misc,
}
