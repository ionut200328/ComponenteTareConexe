import java.util.*;

public class ComponenteTareConexe {
    private int s;         // Declare s

    static class Graf{
        private int[] t1;      // Declare t1
        private int[] t2;      // Declare t2
        private int[] p;       // Declare p
        private ArrayList<ArrayList<Integer>> ListaAdiacenta = new ArrayList<>();
    }

    private static Vector<Node> nodes;
    static ArrayList<ArrayList<Integer>>getListaAdiacenta(Graf graf)
    {
        return graf.ListaAdiacenta;
    }
    private static Vector<Vector<Integer>>AdjencyMatrix;
    private static ArrayList<ArrayList<Integer>>componenteConexe;
    private static Graf grafRedus;

    void createListaAdiacenta(Graf graf)
    {
        //read adjency matrix from file

        FilePrint filePrint=new FilePrint();
        AdjencyMatrix=filePrint.FileRead();

        nodes = new Vector<Node>();
        for(int i=0;i<AdjencyMatrix.size();i++)
        {
            nodes.add(new Node(i+1));
        }

        if(AdjencyMatrix.size()==0)
        {
            return;
        }
        graf.ListaAdiacenta.clear();
        for(int i=0;i<AdjencyMatrix.size();i++)
        {
            ArrayList<Integer>list=new ArrayList<Integer>();
            for(int j=0;j<AdjencyMatrix.size();j++)
            {
                if(AdjencyMatrix.get(i).get(j)==1)
                {
                    list.add(j);
                }
            }
            graf.ListaAdiacenta.add(list);
        }
    }

    public Graf InversulGrafului(Graf graf)
    {
        Graf grafInvers=new Graf();
        grafInvers.ListaAdiacenta=new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<graf.ListaAdiacenta.size();i++)
        {
            ArrayList<Integer>list=new ArrayList<Integer>();
            grafInvers.ListaAdiacenta.add(list);
        }
        for(int i=0;i<graf.ListaAdiacenta.size();i++)
        {
            for(int j=0;j<graf.ListaAdiacenta.get(i).size();j++)
            {
                grafInvers.ListaAdiacenta.get(graf.ListaAdiacenta.get(i).get(j)).add(i);
            }
        }
        return grafInvers;
    }

    public ArrayList<Integer> PTDF(Graf graf) {
        s = 1;
        int t = 1;
        graf.t1 = new int[graf.ListaAdiacenta.size()];
        graf.t2 = new int[graf.ListaAdiacenta.size()];
        graf.p = new int[graf.ListaAdiacenta.size()];

//        ArrayList<Queue<Integer>> A = new ArrayList<Queue<Integer>>();
//        for (int i = 0; i < graf.ListaAdiacenta.size(); i++) {
//            Queue<Integer> queue = new LinkedList<Integer>();
//            for(int a:graf.ListaAdiacenta.get(i))
//            {
//                queue.add(a);
//            }
//            //sort queue
//            Collections.sort((LinkedList<Integer>) queue);
//
//            A.add(queue);
//        }

        Vector<Integer> U = new Vector<Integer>();
        Stack<Integer> V = new Stack<Integer>();
        ArrayList<Integer> PTDF = new ArrayList<>();
        for (int i = 0; i < graf.ListaAdiacenta.size(); i++) {
            if(i+1 == s)
            {
                continue;
            }
            U.add(i + 1);
        }
        for (int i = 0; i < graf.ListaAdiacenta.size(); i++) {
            graf.t1[i] = Integer.MAX_VALUE;
            graf.t2[i] = Integer.MAX_VALUE;
            graf.p[i] = 0;
        }
        graf.t1[s - 1] = t;
        V.push(s);

        while (PTDF.size()!=nodes.size())
        {
            while (!V.isEmpty())
            {
                int x=V.peek();
                int y;

                if(graf.ListaAdiacenta.get(x-1).isEmpty())
                {
                    V.pop();
                    PTDF.add(x);
                    ++t;
                    graf.t2[x-1]=t;
                    continue;
                }

//                if(A.get(x-1).isEmpty())
//                {
//                    V.pop();
//                    PTDF.add(x);
//                    ++t;
//                    graf.t2[x-1]=t;
//                    continue;
//                }
//                y=A.get(x-1).peek()+1;

                y=graf.ListaAdiacenta.get(x-1).get(0)+1;
                if(U.contains(y))
                {
//                    A.get(x-1).remove();
//                    if(!A.get(y-1).isEmpty())
//                    {
//                        A.get(y-1).remove(x-1);
//                    }

                    U.remove(U.indexOf(y));
                    V.push(y);
                    graf.p[y-1]=x;
                    ++t;
                    graf.t1[y-1]=t;
                }
                else
                {
                    V.pop();
                    PTDF.add(x);
                    ++t;
                    graf.t2[x-1]=t;
                }
            }
            if(!U.isEmpty())
            {
                s=SelectSursa(graf, U);
                U.remove(U.indexOf(s));
                V.push(s);
                ++t;
                graf.t1[s-1]=t;
            }
        }
        return PTDF;
    }

    private int SelectSursa(Graf graf, Vector<Integer> U)
    {
        int s=1;
        int max=0;
        for(int i=0;i<graf.ListaAdiacenta.size();i++)
        {
            if(graf.t2[i]>max && U.contains(i+1))
            {
                max=graf.t2[i];
                s=i+1;
            }
        }
        return s;
    }

    ArrayList<ArrayList<Integer>> determinaComponenteConexe(Graf invers, Graf graf) {

        ArrayList<ArrayList<Integer>> componenteConexe = new ArrayList<ArrayList<Integer>>();

        Vector<Integer> U = new Vector<Integer>();
        Stack<Integer> V = new Stack<Integer>();
        Vector<Integer> W = new Vector<Integer>();
        int p = 1;
        ArrayList<Integer> N = new ArrayList<Integer>();

        for (int i = 0; i < invers.ListaAdiacenta.size(); i++) {
            U.add(i + 1);
        }
        s=SelectSursa(graf, U);
        U.remove(U.indexOf(s));

//        ArrayList<Queue<Integer>> A = new ArrayList<Queue<Integer>>();
//        for (int i = 0; i < invers.ListaAdiacenta.size(); i++) {
//            Queue<Integer> queue = new LinkedList<Integer>();
//            for(int a:invers.ListaAdiacenta.get(i))
//            {
//                queue.add(a);
//            }
//            A.add(queue);
//        }

        V.push(s);
        N.add(s);

        while (W.size() != AdjencyMatrix.size())
        {
            while (!V.isEmpty())
            {
                int x=V.peek();
                int y;

                if(invers.ListaAdiacenta.get(x-1).isEmpty())
                {
                    V.pop();
                    W.add(x);
                    continue;
                }
//              y=A.get(x-1).peek()+1;
                boolean ok=false;
                for(int i=0;i<invers.ListaAdiacenta.get(x-1).size();i++) {
                    y = invers.ListaAdiacenta.get(x - 1).get(i) + 1;
                    if (U.contains(y)) {
//                    A.get(x-1).remove();
//                    if(!A.get(y-1).isEmpty())
//                    {
//                        A.get(y-1).remove(x-1);
//                    }

                        U.remove(U.indexOf(y));
                        V.push(y);
                        N.add(y);

                        ok=true;
                        break;
                    }
                }
                if(ok)
                {
                    continue;
                }
                V.pop();
                W.add(x);
            }
            System.out.println("p: "+p);
            System.out.print("N: ");
            for(int i=0;i<N.size();i++)
            {
                System.out.print(N.get(i)+" ");
            }
            componenteConexe.add(new ArrayList<>(N));
            System.out.println();
            if(!U.isEmpty())
            {
                s=SelectSursa(graf, U);
                U.remove(U.indexOf(s));
                V.push(s);
                N.clear();
                N.add(s);
                ++p;
            }
        }
        return componenteConexe;
    }


    private Graf construiesteGrafulRedus(ArrayList<ArrayList<Integer>> componenteConexe, Graf graf) {
        Graf grafRedus = new Graf();

        grafRedus.ListaAdiacenta = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < componenteConexe.size(); i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            grafRedus.ListaAdiacenta.add(list);
        }
        //adauga muchiile din graf in grafRedus si nu repeta muchiile
        for (int i = 0; i < graf.ListaAdiacenta.size(); i++) {
            for (int j = 0; j < graf.ListaAdiacenta.get(i).size(); j++) {
                int x = i + 1;
                int y = graf.ListaAdiacenta.get(i).get(j) + 1;
                int x1 = 0;
                int y1 = 0;
                for (int k = 0; k < componenteConexe.size(); k++) {
                    if (componenteConexe.get(k).contains(x)) {
                        x1 = k + 1;
                    }
                    if (componenteConexe.get(k).contains(y)) {
                        y1 = k + 1;
                    }
                }
                if (x1 != y1) {
                    if (!grafRedus.ListaAdiacenta.get(x1 - 1).contains(y1 - 1)) {
                        grafRedus.ListaAdiacenta.get(x1 - 1).add(y1 - 1);
                    }
                }
            }
        }


        return grafRedus;
    }
    void printListaAdiacenta(ArrayList<ArrayList<Integer>> ListaAdiacenta)
    {
        for(int i=0;i<ListaAdiacenta.size();i++)
        {
            System.out.print((i+1)+": ");
            for(int j=0;j<ListaAdiacenta.get(i).size();j++)
            {
                System.out.print((ListaAdiacenta.get(i).get(j)+1)+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Graf graf=new Graf();
        ComponenteTareConexe componenteTareConexe=new ComponenteTareConexe();
        componenteTareConexe.createListaAdiacenta(graf);
        componenteTareConexe.printListaAdiacenta(graf.ListaAdiacenta);

        System.out.println("PTDF:");
        ArrayList<Integer>PTDF=componenteTareConexe.PTDF(graf);
        for(int i=0;i<PTDF.size();i++)
        {
            System.out.print(PTDF.get(i)+" ");
        }
        Graf invers=componenteTareConexe.InversulGrafului(graf);
        System.out.println("\nInversul grafului:");
        componenteTareConexe.printListaAdiacenta(invers.ListaAdiacenta);
        ArrayList<Integer>PTDFInvers=componenteTareConexe.PTDF(invers);
        System.out.println("PTDF invers:");
        for(int i=0;i<PTDFInvers.size();i++)
        {
            System.out.print(PTDFInvers.get(i)+" ");
        }

        System.out.println("\nComponente tare conexe:");
        componenteConexe=componenteTareConexe.determinaComponenteConexe(invers,graf);

        grafRedus = componenteTareConexe.construiesteGrafulRedus(componenteConexe, graf);
        System.out.println("Graful redus:");
        componenteTareConexe.printListaAdiacenta(grafRedus.ListaAdiacenta);

//        for(int i=0;i<componenteConexe.size();i++)
//        {
//            for(int j=0;j<componenteConexe.get(i).size();j++)
//            {
//                System.out.print(componenteConexe.get(i).get(j)+" ");
//            }
//            System.out.println();
//        }
    }

    public static Graf getGrafRedus() {
        return grafRedus;
    }
    public static ArrayList<ArrayList<Integer>> getComponenteConexe() {
        return componenteConexe;
    }

}
