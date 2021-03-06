package com.company;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ChefWedFinal {

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }


    public static void main(String[] args) throws  java.lang.Exception{
        Reader sc=new Reader();
        int t=sc.nextInt();
        while (t-->0) {

            long len=sc.nextLong();
            long k=sc.nextLong();
            long[] arr=new long[(int)len];

            for (int i=0;i<len;i++){
                arr[i]=sc.nextLong();
            }

            /**  checking if duplicates present or not  */
            long[] arrSorted= Arrays.stream(arr).distinct().toArray();


            /** Storing the frequency of each element */
            Map<Long,Integer> map=new HashMap<>();
            for (int i=0;i<len;i++){
                if (!map.containsKey(arr[i])){
                    map.put(arr[i],1);
                }else{
                    int oldValue=map.get(arr[i]);
                    int newValue=oldValue+1;
                    map.replace(arr[i],oldValue,newValue);
                }
            }

            /** if there is some element with maximum possible repetitions then store that value */
            int maxPossibleFrequency=0;
            int cumulativeFrequencyGreaterThanOne=0;
            for (Map.Entry<Long, Integer> entry:map.entrySet()){
                long element=entry.getKey();
                int frequency=entry.getValue();
                if (frequency>maxPossibleFrequency){
                    maxPossibleFrequency=frequency;
                }
                if (frequency>1){
                    cumulativeFrequencyGreaterThanOne+=frequency;
                }
            }


            if (k==1){
                if (arr.length==arrSorted.length){
                    /** Means it has no duplicates in the array so we only need one table to put them all in it with zero arguments */
                    System.out.println(1);
                }else{
                    int count=0;
                    Set<Long> set=new HashSet<Long>();
                    for (int i=0;i<len;i++){
                        boolean bool=set.add(arr[i]);
                        if (bool==false){
                            count++;
                            set.clear();
                            i--;
                        }
                    }
                    if (set.isEmpty()){
                        //ok
                    }else{
                        count++;
                        set.clear();
                    }
                    System.out.println(count);
                }
            }

            if (k>1){

                /** This is for the case when single table is taken */
                long takingOneTable=k+cumulativeFrequencyGreaterThanOne;
                long takingAllTable=k*maxPossibleFrequency;
                // long min=Math.min(takingAllTable,takingOneTable); //Case when taken n table. but it's never efficient.
                System.out.println(takingOneTable);



                /** Other way to find is by traversing each element and then keeping different elements in different box */

            }


        }
    }

}
