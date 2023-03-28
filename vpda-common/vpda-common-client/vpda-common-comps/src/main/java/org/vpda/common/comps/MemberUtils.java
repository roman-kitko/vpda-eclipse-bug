package org.vpda.common.comps;

public final class MemberUtils {
    
    private MemberUtils(){}
    
    public static String getMemberId(String parentId, String localId) {
        return parentId + '.' + localId; 
    }
    
    public static String getLocalMemberId(String memberId) {
        int indexOfDot = memberId.lastIndexOf('.');
        if (indexOfDot == -1) {
            return memberId;
        }
        return memberId.substring(indexOfDot + 1);
    }
    
    public static String[] resolveIdsPaths(String memberId){
        return memberId.split("\\.");
    }
    
    public static String makeId(String[] localIds) {
        if(localIds.length == 0) {
            throw new IllegalArgumentException("Empty arrays send for making id");
        }
        if(localIds.length == 1) {
            return localIds[0];
        }
        if(localIds.length == 2) {
            return getMemberId(localIds[0], localIds[1]);
        }
        return String.join(".", localIds);
    }

}
