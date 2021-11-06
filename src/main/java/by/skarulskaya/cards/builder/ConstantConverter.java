package by.skarulskaya.cards.builder;

class ConstantConverter {
    public static String convert(String string) {
        StringBuilder sb = new StringBuilder(string.trim());
        if (string.matches(".+[A-Z].+")) {
            for (int i = 0; i < sb.length(); i++) { //todo while
                if (Character.isUpperCase(sb.charAt(i))) {
                    sb.insert(i, "_");
                    break;
                }
            }
        }
        return sb.toString().toUpperCase();
    }
}
