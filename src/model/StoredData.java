package model;

import javax.microedition.rms.*;

/**
 * 
 * @author kawa
 */
public class StoredData
{
    /** Record ID for the player one's name */
    public static int RECORD_PLAYER1 = 1;
    /** Record ID for the player two's name */
    public static int RECORD_PLAYER2 = 2;
    /** Record ID for the player three's name */
    public static int RECORD_PLAYER3 = 3;
    /** Record ID for the player four's name */
    public static int RECORD_PLAYER4 = 4;
    /** Record ID for the starting amount of health points */
    public static int RECORD_INITHP = 5;
    /** Record ID for the maximum poison counter */
    public static int RECORD_MAXPSN = 6;
    
    /** Name of the Record Store Object */
    private static String RECORD_NAME = "CardGameCounterTest1";
    
    /** Record Store */
    private RecordStore records = null ;
    
   
    /**
     * Constructor.
     * 
     * Look for the record store, create the initial records if necessary.
     */
    public StoredData ()
    {
        
        try
        {
            records = RecordStore.openRecordStore(RECORD_NAME, true);
            create();
        }  
        catch (RecordStoreFullException ex)
        {
            ex.printStackTrace();
        } 
        catch (RecordStoreNotFoundException ex)
        {
            ex.printStackTrace();
        } 
        catch (RecordStoreNotOpenException ex)
        {
            ex.printStackTrace();
        }
        catch (RecordStoreException ex)
        {
            ex.printStackTrace();
        }
        
        
        
    }
    
    /**
     * Create records with default values
     * @throws RecordStoreNotOpenException
     * @throws RecordStoreFullException
     * @throws RecordStoreException 
     */
    private void create() throws RecordStoreNotOpenException, RecordStoreFullException, RecordStoreException
    {
        String value;
        byte[] bytes;
        
        if(records != null)
        {
            if(records.getNumRecords() == 0)
            {
                System.out.println("Create - Zero records");
                for(int i = 0; i < RECORD_MAXPSN; i++)
                {
                    value = getDefaultValue(i+1);
                    bytes = value.getBytes();
                    int j = records.addRecord(bytes, 0, bytes.length);
                    System.out.println("Create - ID = "+j+" - Name = "+value);
                }
            }
        }
    }
    
    /**
     * Get the default value for a record
     * @param what The record
     * @return The default value for this record
     */
    private String getDefaultValue(int what)
    {
        switch(what)
        {
            case 1 : return "Player 1";
            case 2 : return "Player 2";
            case 3 : return "Player 3";
            case 4 : return "Player 4";
            case 5 : return "20";
            case 6 : return "10";
            default : return null;
        }
    }
    
    
    /**
     * Read the record
     * @param what The record ID
     * @return The record value
     */
    public String read (int what) 
    {
        String str = "";
        
        System.out.println("Piou - " + what);
        
        if(records != null)
        {
            try
            {
                str = new String(records.getRecord(what));
            } catch (RecordStoreNotOpenException ex)
            {
                ex.printStackTrace();
            } catch (InvalidRecordIDException ex)
            {
                ex.printStackTrace();
            } catch (RecordStoreException ex)
            {
                ex.printStackTrace();
            }
        }
        
        return str;
    }
    
    /**
     * Override a record
     * @param what Record ID
     * @param newValue New value for this record
     */
    public void write(int what, String newValue)
    {
        byte[] b = newValue.getBytes();
        
        if(records != null)
        {
            try
            {
                System.out.println("Write - ID = "+what+" - value = "+newValue);
                records.setRecord(what, b, 0, b.length);
            } catch (RecordStoreNotOpenException ex)
            {
                ex.printStackTrace();
            } catch (InvalidRecordIDException ex)
            {
                ex.printStackTrace();
            } catch (RecordStoreFullException ex)
            {
                ex.printStackTrace();
            } catch (RecordStoreException ex)
            {
                ex.printStackTrace();
            } 
        }
    }
    
}
