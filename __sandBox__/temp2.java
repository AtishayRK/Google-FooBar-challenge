/**
 * CREATED BY ROSHAN SINGH
 *
 */
import java.io.*;
import java.util.*;
import java.math.BigInteger;

class fraction{
    long num,den;
    double dec;
    public fraction(long a,long b){
        long hcf = getGCD(a,b);
        a = a/hcf;
        b = b/hcf;
        this.num = a;
        this.den = b;
        dec = ((double)a)/b;
    }
    public fraction(){
        this.num = 0;
        this.den = 1;
        dec = 0;
    }
    public fraction(fraction o){
        this.num = o.num;
        this.den = o.den;
        this.dec = ((double)num)/den;
    }
    fraction multiply(fraction o){
        long a = o.num*this.num;
        long b = o.den*this.den;
        long hcf = getGCD(a,b);
        a = a/hcf;
        b = b/hcf;
        return new fraction(a,b);
    }
    fraction add(fraction o){
        long lcm = o.den*this.den;
        long sum = (o.num*this.den)+(this.num*o.den);
        long hcf = getGCD(sum,lcm);
        lcm = lcm/hcf;
        sum = sum/hcf;
        return new fraction(sum,lcm);
    }
    fraction sub(fraction o){
        long lcm = o.den*this.den;
        long sum = (this.num*o.den)-(o.num*this.den);
        long hcf = getGCD(sum,lcm);
        lcm = lcm/hcf;
        sum = sum/hcf;
        return new fraction(sum,lcm);
    }
    fraction div(fraction o){
        long a = this.num*o.den;
        long b = this.den*o.num;
        long hcf = getGCD(a,b);
        a = a/hcf;
        b = b/hcf;
        return new fraction(a,b);
    }
    static long getGCD(long n1, long n2) {
        if (n2 == 0) {
            return n1;
        }
        return getGCD(n2, n1 % n2);
    }
    public String toString(){
        return " ["+this.num+"/"+this.den+"] ";
    }
}
class Solution {
    static long getGCD(long n1, long n2) {
        if (n2 == 0) {
            return n1;
        }
        return getGCD(n2, n1 % n2);
    }
    public static fraction[][] invert(fraction a[][]) {
        int n = a.length;
        fraction x[][] = new fraction[n][n];
        fraction b[][] = new fraction[n][n];
        int index[] = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                b[i][j] = new fraction();
                x[i][j] = new fraction();
            }
        }
        for (int i=0; i<n; ++i)
            b[i][i] = new fraction(1,1);
        gaussian(a, index);

        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k] = b[index[j]][k]
                            .sub(a[index[j]][i]
                                    .multiply(b[index[i]][k]));
        for (int i=0; i<n; ++i)
        {
            x[n-1][i] = b[index[n-1]][i].div(a[index[n-1]][n-1]);
            for (int j=n-2; j>=0; --j)
            {
                x[j][i] = new fraction(b[index[j]][i]);
                for (int k=j+1; k<n; ++k)
                {
                    x[j][i] = x[j][i].sub(a[index[j]][k].multiply(x[k][i]));
                }
                x[j][i] = x[j][i].div(a[index[j]][j]);
            }
        }
        return x;
    }
    public static void gaussian(fraction a[][], int index[])
    {
        int n = index.length;

        fraction c[] = new fraction[n];
        for (int i = 0; i < n; i++) {
            c[i] = new fraction();
        }
        for (int i=0; i<n; ++i)
            index[i] = i;
        for (int i=0; i<n; ++i) {
            fraction c1 = new fraction();
            for (int j=0; j<n; ++j)
            {
                fraction c0 = new fraction(Math.abs(a[i][j].num),Math.abs(a[i][j].den));
                if (((c0.num*1.0)/c0.den) > ((c1.num*1.0)/c1.den)) c1 = new fraction(c0);
            }
            c[i] = new fraction(c1);
        }

        int k = 0;
        for (int j=0; j<n-1; ++j)
        {
            fraction pi1 = new fraction();
            for (int i=j; i<n; ++i)
            {

                fraction pi0 = new fraction(Math.abs(a[i][j].num),Math.abs(a[i][j].den));
                pi0 = pi0.div(c[index[i]]);
                if (((pi0.num*1.0)/pi0.den) > ((pi1.num*1.0)/pi1.den))
                {
                    pi1 = new fraction(pi0);
                    k = i;
                }
            }

            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i)
            {

                fraction pj = a[index[i]][j].div(a[index[j]][j]);
//                System.out.println(pj);

                a[index[i]][j] = new fraction(pj);

                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] = a[index[i]][l].sub(pj.multiply(a[index[j]][l]));

            }

        }
//        for(fraction[] ix : a) {
//            for (fraction jx : ix) System.out.print(jx);
//            System.out.println("");
//        }
    }

    public static int[] solution(int[][] m) {
        // Your code here
        if(m[0].length == 1) return new int[]{1,1};
        fraction[][] p = new fraction[m.length][m.length];
        fraction[][] p2 = new fraction[m.length][m.length];
        fraction[][] p3 = new fraction[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                p3[i][j] = new fraction();
                p2[i][j] = new fraction();
                p[i][j] = new fraction();
            }
        }
        double[] summ = new double[m.length];
//        Map<Integer, Integer> map = new HashMap<>();
        int[] map = new int[m.length];
        for(int i = 0;i<map.length;i++) map[i] = -1;

        for (int i = 0; i < m.length; i++) {
            int sum = 0;
            for (int j = 0; j < m.length; j++) sum += m[i][j];
            if (sum == 0) {
                summ[i] = 0;
                p[i][i] = new fraction(1,1);
                p2[i][i] = new fraction(1,1);
                continue;
            }
            for (int j = 0; j < m.length; j++) {
                p[i][j] = new fraction(m[i][j] , sum);
                p2[i][j] = p[i][j];
                summ[i] = sum;
            }
        }
        int count = 0;
        for (int i = 0; i < m.length; i++) {
            if (summ[i] == 0) {
//                p[count][count] = 1.0;
                map[i] = count;
//                map.put(i, count);
                count++;
            }
        }
        int div = count;
        for (int i = 0; i < m.length; i++) {
            if (map[i] == -1) {
                map[i] = count;
                count++;
            }
        }

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                p3[map[i]][map[j]] = p2[i][j];
            }
        }
//        for (fraction[] i : p3){
//            for (fraction j : i) System.out.print(j);
//            System.out.println("");
//        }
//        for (double[] i : p) System.out.println(Arrays.toString(i));
//        System.out.println("`````````````"+map);
//        for (double[] i : p3) System.out.println(Arrays.toString(i));
        fraction[][] Q = new fraction[m.length-div][m.length-div];
        fraction[][] R = new fraction[m.length-div][div];
        for (int i = 0; i < m.length-div; i++) {
            for (int j = 0; j < m.length-div; j++) {
                Q[i][j] = p3[div+i][div+j];
            }
        }
        for (int i = 0; i < m.length-div; i++) {
            for (int j = 0; j < div; j++) {
                R[i][j] = p3[div+i][j];
            }
        }
        fraction one = new fraction(1,1);
        fraction zero = new fraction();
        for (int i = 0; i < Q.length; i++) {
            for (int j = 0; j < Q.length; j++) {
                Q[i][j] = ((i==j)?one:zero).sub(Q[i][j]);
            }
        }
//        double[][] F = null;
//        Solution.N = F.length;

        fraction[][] F = invert(Q);//inverse(Q,F);
//        for (fraction[] i : F){
//            for (fraction j : i) System.out.print(j);
//            System.out.println("");
//        }
//        for (double[] i : F) System.out.println(Arrays.toString(i));
        fraction[][] FR = new fraction[F.length][div];
        for (int i = 0; i < F.length; i++) {
            for (int j = 0; j < div; j++) {
                FR[i][j] = new fraction();
            }
        }

        for (int i = 0; i < F.length; i++) {
            for (int j = 0; j < div; j++) {
                for (int k = 0; k < F.length; k++) {
//                    double aaa = ;
//                    double xxx = ;
//                    System.out.println(FR[i][j]+" "+i+" "+j);
                    FR[i][j] = FR[i][j]
                            .add(F[i][k]
                                    .multiply(R[k][j]));
                }
            }
        }
//        for (fraction[] i : FR){
//            for (fraction j : i) System.out.print(j);
//            System.out.println("");
//        }
        long lcm = 1;
        for (fraction i : FR[0]) {
            long hcf = getGCD(lcm,i.den);
            lcm = (lcm*i.den)/hcf;
        }
        int[] result = new int[FR[0].length+1];
        for (int i = 0; i < FR[0].length; i++) {
            result[i] = (int)(FR[0][i].num*(lcm/FR[0][i].den));
        }
        result[result.length-1] = (int)lcm;
        return result;
//        return new int[6];
    }
}
class Main{
    static class FastScanner {  // for string + number
        BufferedReader br;
        StringTokenizer st;
        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        public FastScanner(String file)  throws IOException{
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        }
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException  e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
        int nextInt() {
            return Integer.parseInt(next());
        }
        long nextLong() {
            return Long.parseLong(next());
        }
        double nextDouble() {
            return Double.parseDouble(next());
        }
        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
    public static void main(String[] args) throws IOException {
        FastScanner s = new FastScanner();
//        FastScanner s = new FastScanner("INPUT");
        OutputStream outputStream = System.out;
//        OutputStream outputStream = new FileOutputStream("OUTPUT");
        PrintWriter o = new PrintWriter(outputStream);
        // start
//        int t = s.nextInt();
//        while(t-->0){
//            int n = s.nextInt();
//            String[] st = new String[n];
//        for (int i = 0; i < n; i++) {
//            st[i] = s.next();
//        }
        long time =  System.nanoTime();
        int[] out = Solution.solution(//new int[][]{{0, 1, 0, 0, 0, 1}, {4, 0, 0, 3, 2, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}}
                new int[][]{
                        {0}
                }
        );
        long escap = System.nanoTime()  - time;
        o.println(Arrays.toString(out));
        o.println((escap/1000000)+"ms");
//        }
        // end
        o.close();
    }
}