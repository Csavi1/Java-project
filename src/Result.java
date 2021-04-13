public class Result implements Comparable<Result>{
    public Result(String name, int tokens) {
        Name = name;
        Tokens = tokens;
    }

    public String Name;
    public int Tokens;

    @Override
    public int compareTo(Result o) {
        if (o.Tokens > this.Tokens)
            return 1;
        if (o.Tokens < this.Tokens)
            return -1;
        return 0;
    }
}
