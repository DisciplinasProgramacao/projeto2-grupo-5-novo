package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GrafoMutavel extends Grafo {
    protected final String PATH = "docs/grafos/";

    public GrafoMutavel(String nome) {
        super(nome);
    }

    public void carregar(String nome) throws IOException {
        String path = PATH + nome + ".csv";
        File arquivo = new File(path);

        FileReader file = new FileReader(arquivo);
        BufferedReader reader = new BufferedReader(file);


        String line;
        line = reader.readLine();
        int id;
        int idOrigem = 0;
        boolean firstLine = true;
        String[] splitMatrix;

        while(line != null) {
            splitMatrix = line.split(";");
            boolean firstValue = true;

            if (firstLine) {
                for (String matrix : splitMatrix) {
                    if(!matrix.equals(" ")) {
                        id = Integer.parseInt(matrix);
                        this.addVertice(id);
                    }
                }
                firstLine = false;
            } else {
                for (String matrix : splitMatrix) {
                    if (firstValue) {
                        if(!matrix.equals(" ")) {
                            idOrigem = Integer.parseInt(matrix);
                            firstValue = false;
                        }
                    } else if (matrix.equals("1")) {
                        id = Integer.parseInt(matrix);
                        this.addAresta(idOrigem, id);
                    }
                }

                line = reader.readLine();
            }
        }
        reader.close();
        file.close();
    }


    public void salvar(String nome) throws IOException {
        String path = PATH + nome + ".csv";

        FileWriter writer = new FileWriter(path);
        BufferedWriter bfWriter = new BufferedWriter(writer);
        int cont = 0;
        Vertice[] vertices = new Vertice[this.ordem()];
        vertices = this.vertices.allElements(vertices);
        Integer[] keys = this.vertices.allKeys();
        String keyString;

        bfWriter.append(" ;");

        for(Vertice vertice : vertices) {

            if(cont == keys.length - 1) {
                bfWriter.append((char) vertice.getId()).append("\n");
            } else {
                bfWriter.append((char) vertice.getId()).append(";");
            }
            cont++;
        }

        cont = 0;

        for(Integer keyOrigem : keys) {
            keyString = keyOrigem.toString();
            bfWriter.append(keyString).append(";");
            for(Integer keyDestino : keys) {
                if((this.existeAresta(keyOrigem, keyDestino)) != null) {
                    if (cont == keys.length - 1)
                        bfWriter.append("1\n");
                    else
                        bfWriter.append("1;");
                } else {
                    if (cont == keys.length - 1)
                        bfWriter.append("0\n");

                    else
                        bfWriter.append("0;");
                }
                cont++;
            }
            cont = 0;
        }
        bfWriter.close();
        writer.close();
    }

    /**
     * Adiciona, se poss??vel, um v??rtice ao grafo. O v??rtice ?? auto-nomeado com o pr??ximo id dispon??vel.
     * @param id id do vertice a ser adicionado.
     * @return true se adicionou e false se n??o
     */
    public boolean addVertice(int id){
        Vertice novo = new Vertice(id);
        return this.vertices.add(id, novo);
    }

    public boolean removeVertice(int id) {
        return this.vertices.del(id);
    }

    /**
     * Adiciona uma aresta entre dois v??rtices do grafo. 
     * N??o verifica se os v??rtices pertencem ao grafo.
     * @param origem V??rtice de origem
     * @param destino V??rtice de destino
     * @return true se adicionou e false se n??o
     */
    public boolean addAresta(int origem, int destino){
        boolean adicionou = false;
        Vertice saida = this.existeVertice(origem);
        Vertice chegada = this.existeVertice(destino);

        if(saida != null && chegada != null) {
            adicionou = saida.addAresta(1, destino);
        }

        return adicionou;
    }
    
    public boolean delAresta(int origem, int destino) {
        Vertice saida = this.existeVertice(origem);
        Vertice chegada = this.existeVertice(destino);
        return saida.delAresta(destino) && chegada.delAresta(destino);
    }
}
