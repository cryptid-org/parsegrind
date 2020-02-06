package cryptid.parsegrind.valgrind.util;

import cryptid.parsegrind.valgrind.model.ValgrindError;
import cryptid.parsegrind.valgrind.model.ValgrindErrorKind;

import java.util.ArrayList;
import java.util.List;

public class ValgrindErrorList {
    private List<ValgrindError> errors;

    public ValgrindErrorList(List<ValgrindError> errors) {
        this.errors = errors;
    }

    public int getOverlapErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.Overlap);
    }

    public List<ValgrindError> getOverlapErrors() {
        return getErrorsByKind(ValgrindErrorKind.Overlap);
    }

    public int getSyscallParamErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.SyscallParam);
    }

    public List<ValgrindError> getSyscallParamErrors() {
        return getErrorsByKind(ValgrindErrorKind.SyscallParam);
    }

    public int getInvalidFreeErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.InvalidFree);
    }

    public List<ValgrindError> getInvalidFreeErrors() {
        return getErrorsByKind(ValgrindErrorKind.InvalidFree);
    }

    public int getMismatchedFreeErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.MismatchedFree);
    }

    public List<ValgrindError> getMismatchedFreeErrors() {
        return getErrorsByKind(ValgrindErrorKind.MismatchedFree);
    }

    public int getUninitializedValueErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.UninitValue);
    }

    public List<ValgrindError> getUninitializedValueErrors() {
        return getErrorsByKind(ValgrindErrorKind.UninitValue);
    }

    public int getUninitializedConditionErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.UninitCondition);
    }

    public List<ValgrindError> getUninitializedConditionErrors() {
        return getErrorsByKind(ValgrindErrorKind.UninitCondition);
    }

    public int getInvalidReadErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.InvalidRead);
    }

    public List<ValgrindError> getInvalidReadErrors() {
        return getErrorsByKind(ValgrindErrorKind.InvalidRead);
    }

    public int getInvalidWriteErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.InvalidWrite);
    }

    public List<ValgrindError> getInvalidWriteErrors() {
        return getErrorsByKind(ValgrindErrorKind.InvalidWrite);
    }

    public int getLeakDefinitelyLostErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.Leak_DefinitelyLost);
    }

    public List<ValgrindError> getLeakDefinitelyLostErrors() {
        return getErrorsByKind(ValgrindErrorKind.Leak_DefinitelyLost);
    }

    public int getLeakPossiblyLostErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.Leak_PossiblyLost);
    }

    public List<ValgrindError> getLeakPossiblyLostErrors() {
        return getErrorsByKind(ValgrindErrorKind.Leak_PossiblyLost);
    }

    public int getLeakStillReachableErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.Leak_StillReachable);
    }

    public List<ValgrindError> getLeakStillReachableErrors() {
        return getErrorsByKind(ValgrindErrorKind.Leak_StillReachable);
    }

    public int getLeakIndirectlyLostErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.Leak_IndirectlyLost);
    }

    public List<ValgrindError> getLeakIndirectlyLostErrors() {
        return getErrorsByKind(ValgrindErrorKind.Leak_IndirectlyLost);
    }

    public int getRaceErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.Race);
    }

    public List<ValgrindError> getRaceErrors() {
        return getErrorsByKind(ValgrindErrorKind.Race);
    }

    public int getUnlockUnlockedErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.UnlockUnlocked);
    }

    public List<ValgrindError> getUnlockUnlockedErrors() {
        return getErrorsByKind(ValgrindErrorKind.UnlockUnlocked);
    }

    public int getUnlockForeignErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.UnlockForeign);
    }

    public List<ValgrindError> getUnlockForeignErrors() {
        return getErrorsByKind(ValgrindErrorKind.UnlockForeign);
    }

    public int getUnlockBogusErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.UnlockBogus);
    }

    public List<ValgrindError> getUnlockBogusErrors() {
        return getErrorsByKind(ValgrindErrorKind.UnlockBogus);
    }

    public int getPthAPIErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.PthAPIerror);
    }

    public List<ValgrindError> getPthAPIErrors() {
        return getErrorsByKind(ValgrindErrorKind.PthAPIerror);
    }

    public int getLockOrderErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.LockOrder);
    }

    public List<ValgrindError> getLockOrderErrors() {
        return getErrorsByKind(ValgrindErrorKind.LockOrder);
    }

    public int getMiscErrorCount() {
        return getErrorCountByKind(ValgrindErrorKind.Misc);
    }

    public List<ValgrindError> getMiscErrors() {
        return getErrorsByKind(ValgrindErrorKind.Misc);
    }

    public int getErrorCount() {
        if (errors == null)
            return 0;

        return errors.size();
    }

    public int getErrorCountByKind(ValgrindErrorKind valgrindErrorKind) {
        if (errors == null)
            return 0;

        int count = 0;
        for (ValgrindError error : errors) {
            if (error.getKind() == null)
                continue;

            if (error.getKind().equals(valgrindErrorKind))
                count++;
        }

        return count;
    }

    public List<ValgrindError> getErrorsByKind(ValgrindErrorKind valgrindErrorKind) {
        if (errors == null || errors.isEmpty())
            return null;

        List<ValgrindError> result = new ArrayList<ValgrindError>();

        for (ValgrindError error : errors)
            if (error.getKind().equals(valgrindErrorKind))
                result.add(error);

        if (result.isEmpty())
            return null;

        return result;
    }

    public int getDefinitelyLeakedBytes() {
        if (errors == null)
            return 0;

        int bytes = 0;

        for (ValgrindError error : errors) {
            if (error.getKind() != ValgrindErrorKind.Leak_DefinitelyLost)
                continue;

            if (error.getLeakedBytes() == null)
                continue;

            bytes += error.getLeakedBytes().intValue();
        }

        return bytes;
    }

    public int getPossiblyLeakedBytes() {
        if (errors == null)
            return 0;

        int bytes = 0;

        for (ValgrindError error : errors) {
            if (error.getKind() != ValgrindErrorKind.Leak_PossiblyLost)
                continue;

            if (error.getLeakedBytes() == null)
                continue;

            bytes += error.getLeakedBytes().intValue();
        }

        return bytes;
    }

    public int getIndirectlyLeakedBytes() {
        if (errors == null)
            return 0;

        int bytes = 0;

        for (ValgrindError error : errors) {
            if (error.getKind() != ValgrindErrorKind.Leak_IndirectlyLost)
                continue;

            if (error.getLeakedBytes() == null)
                continue;

            bytes += error.getLeakedBytes().intValue();
        }

        return bytes;
    }

    public int getStillReachableLeakedBytes() {
        if (errors == null)
            return 0;

        int bytes = 0;

        for (ValgrindError error : errors) {
            if (error.getKind() != ValgrindErrorKind.Leak_StillReachable)
                continue;

            if (error.getLeakedBytes() == null)
                continue;

            bytes += error.getLeakedBytes().intValue();
        }

        return bytes;
    }

    @SuppressWarnings("deprecation")
    public int getLeakedBytes(ValgrindErrorKind kind, String executable) {
        if (errors == null)
            return 0;

        int bytes = 0;

        for (ValgrindError error : errors) {
            if (error.getKind() != kind)
                continue;

            if (!error.getExecutable().equals(executable))
                continue;

            if (error.getLeakedBytes() == null)
                continue;

            bytes += error.getLeakedBytes().intValue();
        }

        return bytes;
    }
}
