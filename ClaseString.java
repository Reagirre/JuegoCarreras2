public class ClaseString {

    private String text;

    public ClaseString(String text) {
        this.text = text;
    }

   public String toString(){
        return text;
   }


    public int getLength(){
        return text.length();
    }

    public char charAt(int index){
        return text.charAt(index);
    }

    public String toUpperCase(){
        return text.toUpperCase();
    }

    public String getSubString(int start, int end){
        return text.substring(start, end);
    }

    public String replace(String oldText, String newText){
        return text.replace(oldText, newText);
    }

    public String trim(){
        return text.trim();
    }

    public boolean esPalindromo(String word){
        int length = word.length();
        for (int i = 0; i < length/2; i++) {
            if (word.charAt(i) != word.charAt(length-i-1)) {
                return false;
            }
        }
        return true;
    }

    public void setText(String text) {
        this.text = text;
    }


}
