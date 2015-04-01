package model;

/**
 * A simple counter.
 * 
 * @author kawa
 */
public class Counter
{
    
    /**
     * Minimum and maximum values are effective
     */
    public static int DEFAULT = 0 ;
    
    /**
     * No minimum value (equals to -infinite)
     */
    public static int NO_MIN = 1 ;
    
    /**
     * No maximum value (equals to +infinite)
     */
    public static int NO_MAX = 2 ;
    
    /**
     * No minimum and maximum values
     */
    public static int NO_MIN_MAX = 3;
    
    /** 
     * Current value of the counter 
     */
    private int value ;
    
    /** 
     * Maximum value of the counter
     */
    private int max ;
    
    /** 
     * Minimum value of the counter 
     */
    private int min ;
    
    /**
     * Incremental value
     */
    private int step ;
    
    /**
     * Parameters of the counter
     */
    private int param ;
    
    
    /**
     * Simple constructor : the step value is 1 and the parameters are DEFAULT
     * 
     * @param value Initial value
     * @param min Minimum value
     * @param max Maximum value
     */
    public Counter (int value, int min, int max)
    {
        this(value,min,max,1,DEFAULT);
    }
    
    /**
     * Simple constructor : the step value is 1.
     * 
     * @param value Initial value
     * @param min Minimum value
     * @param max Maximum value
     * @param params Parameters
     */
    public Counter (int value, int min, int max, int params)
    {
        this(value,min,max,1,params);
    }
    
   
    
    /**
     * Full constructor
     * 
     * @param value Initial value
     * @param min Minimum value
     * @param max Maximum value
     * @param step Incremental value
     * @param param Parameters
     */
    public Counter (int value, int min, int max, int step, int param)
    {
        this.value = value ;
        this.max = max ;
        this.min = min ;
        this.step = step ;
        this.param = param ;
    }
    
    /**
     * Return if the current value is the minimum of the counter.
     * If the parameters include NO_MIN, this method will always return false.
     * 
     * @return true if the current value is the minimum of the counter, false otherwise.
     */
    public boolean isMin ()
    {
        return (param == NO_MIN || param == NO_MIN_MAX) ? false : value <= min ;
    }
    
    /**
     * Return if the current value is the maximum of the counter.
     * If the parameters include NO_MAX, this method will always return false.
     * 
     * @return true if the current value is the minimum of the counter, false otherwise. 
     */
    public boolean isMax ()
    {
        return (param == NO_MAX || param == NO_MIN_MAX) ? false : value >= max ;
    }

    /**
     * Increase the current value.
     */
    public void incr()
    {
        if(!isMax())
        {
            this.value += this.step;
        }
    }
    
    /**
     * Decrease the current value.
     */
    public void decr()
    {
        if(!isMin())
        {
            this.value -= this.step;
        }
    }
    
    
    //
    // Getters & Setters
    //
    
    public int getMax()
    {
        return max;
    }

    public void setMax(int max)
    {
        this.max = max;
    }

    public int getMin()
    {
        return min;
    }

    public void setMin(int min)
    {
        this.min = min;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }
    
    public String toString ()
    {
        return value + "";
    }
    
    
    
}
