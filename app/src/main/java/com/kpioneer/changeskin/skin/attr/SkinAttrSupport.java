package com.kpioneer.changeskin.skin.attr;

        import android.content.Context;
        import android.util.AttributeSet;

        import com.kpioneer.changeskin.skin.config.Const;

        import java.util.ArrayList;
        import java.util.List;

/**
 * @author xionhgu
 * @version [版本号，2017/1/6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class SkinAttrSupport {
    public static List<SkinAttr> getSkinAttrs(AttributeSet attrs , Context context)
    {
        List<SkinAttr> mSkinAttrs = new ArrayList<>();
        SkinAttrType attrType = null;
        SkinAttr skinAttr = null;
        for(int i = 0 , n = attrs.getAttributeCount();i < n ;i++){

            String attrName = attrs.getAttributeName(i);
            String attrVal = attrs.getAttributeValue(i);
            if(attrVal.startsWith("@")){
                int  id =-1;

                try {
                    id = Integer.parseInt(attrVal.substring(1));
                }catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }

                if(id ==-1){
                    continue;
                }
                String resName = context.getResources().getResourceEntryName(id);

                if(resName.startsWith(Const.SKIN_PREFIX)){
                    attrType = getSupportAttrType(attrName);
                    if(attrType==null) continue;
                    skinAttr = new SkinAttr(resName,attrType);
                    mSkinAttrs.add(skinAttr);
                }
            }
        }
        return mSkinAttrs;
    }

    private static SkinAttrType getSupportAttrType(String attrName) {

        for(SkinAttrType skinAttrType : SkinAttrType.values()){

            if(skinAttrType.getAttrType().equals(attrName))
            {
                return skinAttrType;
            }

        }
        return null;
    }
}
