package pl.edu.wsisiz.darkavenger54;

public class Utility
{
    public static boolean tryParsePort(String str)
    {
        try
        {
            Integer.parseInt(str);
            if (Integer.parseInt(str) >= 1024 && Integer.parseInt(str) <= 65535 )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
}
