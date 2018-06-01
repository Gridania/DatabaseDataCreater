package Utils.DataCreater;

import Utils.DataCreater.templet.Email;
import Utils.DataCreater.templet.Telephone;
import Utils.DataCreater.templet.chineseIDNumber;
import Utils.env_properties;
import Utils.privateRandom;
import java.lang.reflect.Method;

import java.lang.reflect.InvocationTargetException;

public class RandomAdvanceDataCreater {
    public RandomAdvanceDataCreater(){}
    public String returnAdvancedString(String methodString,int range) throws Exception {
        StringBuffer advancedString = null;
        try {
            advancedString = (StringBuffer)this.getClass().getMethod(methodString,int.class)
                    .invoke(this,range);
        } catch (NoSuchMethodException e) {
            try {
                advancedString = (StringBuffer)this.getClass()
                        .getMethod("freeStringType", int.class, String.class)
                        .invoke(this,range,methodString);
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
        }
        if(!env_properties.getEnvironment("toDB").equals("load")){
            advancedString.insert(0,'\'');
            advancedString.append('\'');
        }

        return advancedString.toString();
    }

        public StringBuffer ch_idcard(int range) throws Exception {
        if(range<18)
            throw new Exception("身份证号所在字段长度不能小于18");
        StringBuffer middle=new StringBuffer(18);
        middle.append(chineseIDNumber.getRandomLocalPrefix());
        middle.append(RandomBasicDataCreater.getDate(false));
        middle.append(RandomBasicDataCreater.getFixNumber(3,0,false));
        int checkNum=0;
        char[] charArray = middle.toString().toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            checkNum=checkNum+(charArray[i]-48)* chineseIDNumber.cNUM[i];
        }

        middle.append(chineseIDNumber.cID[checkNum%11]);

        return middle;
    }

    public StringBuffer email(int range) throws Exception {
        StringBuffer sb=new StringBuffer();
        String suffix= Email.getRandomEmailSuffix();
        int r_mus_suffix=range-suffix.length();

        if(r_mus_suffix<0)
            throw new Exception("所在字段长度太小");

        sb.append(RandomBasicDataCreater.getNameStr(r_mus_suffix));
        sb.append(suffix);

        return sb;
    }

    public StringBuffer telephone(int range) throws Exception {
        if(range<11)
            throw new Exception("手机号码所在字段长度不能小于11");
        StringBuffer sb=new StringBuffer(11);
        sb.append(Telephone.getRandomPrefix());
        sb.append(RandomBasicDataCreater.getFixNumber(4,0,false));
        sb.append(RandomBasicDataCreater.getFixNumber(4,0,false));

        return sb;
    }

    public StringBuffer ch_word(int strRange)
    {
        return new StringBuffer(strRange).append(RandomBasicDataCreater.getArbitraryCharacter(strRange,'z'));
    }

    public StringBuffer freeStringType(int range,String type) throws Exception {
        char c[]=type.toCharArray();

        char now,toGet='c';
        int input_length=0,now_length=0;

        StringBuffer Return=new StringBuffer();

        for(int loop=0;loop<c.length;loop++)
        {
            now=c[loop];
            if(now>47&&now<58) {
                if(c[loop+1]>47&&c[loop+1]<58) {
                    now_length=now_length*10+now-48;
                }
                else {
                    now_length =now_length*10+now-48;
                    input_length = input_length + now_length;

                    if(range<input_length)
                        throw new Exception("所在字段长度太小");

                    Return.append(RandomBasicDataCreater.getArbitraryCharacter(now_length,toGet));

                    now_length=0;
                    toGet='c';
                }
            }
            else if(now=='n'||now=='b'||now=='s'||now=='c'||now=='m'||now=='z')
                toGet=now;
            else if (now=='\'') {
                loop=loop+this.Stringinquotation(loop,c,Return);
            }
        }

        return Return;
    }

    private Integer Stringinquotation(int loopo,char[] chars,StringBuffer str) throws Exception {
        char stop='\'';int loop=1;
        while(chars[loopo+loop]!=stop)
        {
            str.append(chars[loopo+loop]);
            loop++;
        }
        return loop;
    }
}