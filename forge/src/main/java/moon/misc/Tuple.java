package moon.misc;

/**
 * Created by Moon on 2/20/2019
 * Ripped from Minecraft's Tuple class, but allows modifying the items in the tuple
 * @param <A>
 * @param <B>
 */

public class Tuple<A, B>
{
    private A a;
    private B b;

    public Tuple(A aIn, B bIn)
    {
        this.a = aIn;
        this.b = bIn;
    }

    /**
     * Get the first Object in the Tuple
     */
    public A getFirst()
    {
        return this.a;
    }

    /**
     * Get the first Object in the Tuple
     */
    public void setFirst(A newValue)
    {
        this.a = newValue;
    }

    /**
     * Get the second Object in the Tuple
     */
    public B getSecond()
    {
        return this.b;
    }

    /**
     * Sets the second Object in the Tuple
     */
    public void setSecond(B newValue)
    {
        this.b = newValue;
    }
}