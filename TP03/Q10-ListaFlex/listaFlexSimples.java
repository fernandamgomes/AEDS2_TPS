import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class listaFlexSimples {
    public static void main(String[] args) throws Exception {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        Lista lista = new Lista();
        // ler linhas stdin
        String filmeIn = sc.nextLine();
        while (isFim(filmeIn) == false) {
            Filme tmp = new Filme();
            tmp.lerArquivo(filmeIn);
            // ler stdin - novo input
            lista.inserirFim(tmp);
            filmeIn = sc.nextLine();
        }
        // SEGUNDA PARTE - LISTA OPERACOES
        filmeIn = sc.nextLine();
        // VARIAVEIS
        int operacoes = Integer.parseInt(filmeIn);
        String filme = "";
        String operacao = "";
        int pos = 0;
        // realizar n operacoes
        while (operacoes > 0) {
            filmeIn = sc.nextLine();
            Filme tmp = new Filme();
            // identificar operacao
            operacao = filmeIn.substring(0, 2);
            // SE OPERACAO FOR DO TIPO INSERIR --> PEGAR NOME FILME
            if ((operacao.equals("II")) || (operacao.equals("IF")) || (operacao.equals("I*"))) {
                if (operacao.equals("I*")) {
                    filme = filmeIn.substring(6);
                } else {
                    filme = filmeIn.substring(3);
                }
                // PREENCHER FILME TMP
                tmp.lerArquivo(filme);
            }
            // REALIZAR OPERACAO
            if (operacao.equals("II")) {
                lista.inserirInicio(tmp);
            } else if (operacao.equals("IF")) {
                lista.inserirFim(tmp);
            } else if (operacao.equals("I*")) {
                pos = Integer.parseInt(filmeIn.substring(3, 5));
                lista.inserir(tmp, pos);
            } else if (operacao.equals("RI")) {
                tmp = lista.removerInicio();
            } else if (operacao.equals("RF")) {
                tmp = lista.removerFim();
            } else if (operacao.equals("R*")) {
                pos = Integer.parseInt(filmeIn.substring(3, 5));
                tmp = lista.remover(pos);
            }
            if ((operacao.equals("RI")) || (operacao.equals("RF")) || (operacao.equals("R*"))) {
                System.out.println("(R) " + tmp.getNome());
            }
            operacoes--;
        }
        // imprimir lista de filmes
        lista.imprimir();
        sc.close();
    }

    // ACHA O FIM DO ARQUIVO
    public static boolean isFim(String str) {
        return ((str.length() == 3) && (str.charAt(0) == 'F') && (str.charAt(1) == 'I')
                && (str.charAt(2) == 'M'));
    }
}

class Filme {
    // ATRIBUTOS
    private String nome;
    private String titulo;
    private Date data;
    private int duracao;
    private String genero;
    private String idioma;
    private String situacao;
    private float orcamento;
    private String[] palavrasChave;

    // CONSTRUTORES
    public Filme(String nome, String titulo, Date data, int duracao, String genero, String idioma,
            String situacao, float orcamento, String[] palavrasChave) {
        this.nome = nome;
        this.titulo = titulo;
        this.data = data;
        this.duracao = duracao;
        this.genero = genero;
        this.idioma = idioma;
        this.situacao = situacao;
        this.orcamento = orcamento;
        this.palavrasChave = palavrasChave;
    }

    public Filme() {
    }

    // GETTERS

    public String getNome() {
        return nome;
    }

    public String getTitulo() {
        return titulo;
    }

    public Date getData() {
        return data;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getGenero() {
        return genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getSituacao() {
        return situacao;
    }

    public float getOrcamento() {
        return orcamento;
    }

    public String[] getPalavrasChave() {
        return palavrasChave;
    }

    // SETTERS
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void setOrcamento(float orcamento) {
        this.orcamento = orcamento;
    }

    public void setPalavrasChave(String[] palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    // CLONE
    public Filme clone() {
        Filme clone = new Filme();
        clone.setNome(this.getNome());
        clone.setTitulo(this.getTitulo());
        clone.setData(this.getData());
        clone.setDuracao(this.getDuracao());
        clone.setGenero(this.getGenero());
        clone.setIdioma(this.getIdioma());
        clone.setSituacao(this.getSituacao());
        clone.setOrcamento(this.getOrcamento());
        clone.setPalavrasChave(this.getPalavrasChave());
        return clone;
    }

    // IMPRIMIR
    public void Imprimir() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.print(this.getNome() + " " + this.getTitulo() + " " + sdf.format(this.getData()) + " "
                + this.getDuracao() + " " + this.getGenero() + " " + this.getIdioma() + " " + this.getSituacao() + " "
                + this.getOrcamento() + " ");

        // IMPRIMIR ARRAY PALAVRAS CHAVE
        System.out.print("[");
        if (this.getPalavrasChave() != null) {
            String[] pChave = this.getPalavrasChave();

            for (int i = 0; i < pChave.length - 1; i++) {
                System.out.print(pChave[i] + ", ");
            }
            System.out.print(pChave[pChave.length - 1]);
        }
        System.out.print("]\n");
    }

    // LER - efetuar a leitura dos atributos de um registro (arquivos html)
    public void lerArquivo(String arquivo) throws Exception {
        // File path - parametro
        File file = new File("/tmp/filmes/" + arquivo);
        Scanner scanner = new Scanner(file, "UTF-8");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // boolean que checa se o arq tem titulo definido, se nao tiver titulo = nome
        boolean temTitulo = false;
        while (scanner.hasNextLine()) {
            // ler arq linha a linha e procurar por atributos
            String line = scanner.nextLine();

            // NOME
            if (line.contains("h2 class")) {
                line = scanner.nextLine();
                this.setNome(removeTag(line).trim().replace("&amp;", ""));
            }

            // DATA DE LANCAMENTO
            else if (line.contains("span class=\"release\"")) {
                line = scanner.nextLine();
                line = line.trim();
                this.setData(sdf.parse(line));
            }

            // DURACAO
            else if (line.contains("runtime")) {
                scanner.nextLine();
                line = scanner.nextLine().trim();
                // VARIAVEIS
                int duracao = 0;
                int i = 0;
                int pos = 0;
                String tmp = "";
                // calculo da duracao em minutos, checar tamanho da string
                // HORAS
                if (line.contains("h")) {
                    i = line.indexOf("h");
                    for (int j = 0; j < i; j++) {
                        if ((line.charAt(j) >= 48) && (line.charAt(j) <= 57)) {
                            tmp = tmp + line.charAt(j);
                            pos++;
                        }
                    }
                    duracao = Integer.parseInt(tmp) * 60;
                    pos++;
                }
                // MINUTOS
                if (line.contains("m")) {
                    i = line.indexOf("m");
                    tmp = "";
                    for (int j = pos; j < i; j++) {
                        if ((line.charAt(j) >= 48) && (line.charAt(j) <= 57)) {
                            tmp = tmp + line.charAt(j);
                        }
                    }
                    duracao = duracao + Integer.parseInt(tmp);
                }
                this.setDuracao(duracao);
            }

            // GENERO
            else if (line.contains("genres")) {
                scanner.nextLine();
                line = scanner.nextLine();
                line = removeTag(line).replace("&nbsp;", "").trim();
                this.setGenero(line);
            }

            // IDIOMA
            else if (line.contains("Idioma")) {
                line = removeTag(line.trim());
                line = line.replace("Idioma original ", "");
                this.setIdioma(line);
            }

            // ORCAMENTO
            else if (line.contains("Orçamento")) {
                line = removeTag(line);
                line = line.replace("Orçamento", "").trim().replaceAll("[,$-]", "");
                if (line.isEmpty()) {
                    this.setOrcamento(0);
                } else {
                    this.setOrcamento(Float.parseFloat(line));
                }
            }

            // PALAVRAS CHAVE
            else if (line.contains("Palavras-chave")) {
                // VARIAVEIS
                String tmp = "";
                // testar se tem a lista de palavras chave
                while ((line.contains("<li><a") == false) && (line.contains("Nenhuma palavra") == false)) {
                    line = scanner.nextLine();
                }

                // se tiver umna lista montar array de palavras chave
                if (line.contains("<li><a")) {
                    while ((line.contains("</ul>") == false)) {
                        line = removeTag(line).trim();
                        if (((line.isEmpty()) == false)) {
                            tmp = tmp + line + ',';
                        }
                        line = scanner.nextLine();
                    }
                    this.setPalavrasChave(tmp.split(","));
                }
            }

            // SITUACAO
            else if (line.contains("strong><bdi>Situação</bdi></strong")) {
                line = removeTag(line);
                line = line.replace("Situação", "").trim();
                this.setSituacao(line);
            }

            // TITULO
            else if (line.contains("<strong>Título original</strong>")) {
                temTitulo = true;
                line = removeTag(line);
                line = line.replace("Título original", "").trim();
                this.setTitulo(line);
            }
        }
        // se apos ler todas as linhas do arquivo o titulo nao for encontrado, titulo =
        // nome
        if (temTitulo == false) {
            this.setTitulo(this.getNome());
        }
        scanner.close();
    }

    // REMOVE TAG
    public static String removeTag(String linha) {
        String linhaMod = "";
        for (int i = 0; i < linha.length(); i++) {
            if (linha.charAt(i) == '<') {
                i++;
                while (linha.charAt(i) != '>') {
                    i++;
                }
            } else {
                linhaMod = linhaMod + linha.charAt(i);
            }
        }
        return linhaMod;
    }
}


class Celula {
    // VARIAVEIS
    public Filme filme; // Elemento inserido na celula.
    public Celula prox; // Aponta a celula prox.

    // Construtor da classe.
    public Celula() {
    }

    // Construtor da classe.
    public Celula(Filme filme) {
        this.filme = filme;
        this.prox = null;
    }
}



class Lista {
    private Celula primeiro;
    private Celula ultimo;

    public Lista() {
		primeiro = new Celula();
		ultimo = primeiro;
	}

    public void inserirInicio(Filme filme) {
        Celula tmp = new Celula(filme);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        }
        tmp = null;
    }


    public void inserirFim(Filme filme) {
        ultimo.prox = new Celula(filme);
        ultimo = ultimo.prox;
    }


    public Filme removerInicio() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover, Lista vazia!");
        }

        Celula tmp = primeiro;
        primeiro = primeiro.prox;
        Filme filme = primeiro.filme;
        tmp.prox = null;
        tmp = null;
        return filme;
    }


    public Filme removerFim() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover. Lista vazia!");
        }

        Celula i;
        for (i = primeiro; i.prox != ultimo; i = i.prox);

        Filme filme = ultimo.filme;
        ultimo = i;
        i = ultimo.prox = null;

        return filme;
    }


    public void inserir(Filme filme, int pos) throws Exception {

        int tamanho = tamanho();

        if (pos < 0 || pos > tamanho) {
            throw new Exception("Erro ao inserir posicao invalida!");
        } else if (pos == 0) {
            inserirInicio(filme);
        } else if (pos == tamanho) {
            inserirFim(filme);
        } else {
            // Caminhar ate a posicao anterior a insercao
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox);

            Celula tmp = new Celula(filme);
            tmp.prox = i.prox;
            i.prox = tmp;
            tmp = i = null;
        }
    }


    public Filme remover(int pos) throws Exception {
        Filme resp;
        int tamanho = tamanho();

        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover (vazia)!");

        } else if (pos < 0 || pos >= tamanho) {
            throw new Exception("Erro ao remover (posicao " + pos + " / " + tamanho + " invalida!");
        } else if (pos == 0) {
            resp = removerInicio();
        } else if (pos == tamanho - 1) {
            resp = removerFim();
        } else {
            // Caminhar ate a posicao anterior a insercao
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox);

            Celula tmp = i.prox;
            resp = tmp.filme;
            i.prox = tmp.prox;
            tmp.prox = null;
            i = tmp = null;
        }

        return resp;
    }

    void imprimir() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int j = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox, j++) {
            System.out.print(
                    "[" + j + "] "
                            + i.filme.getNome() + " " + i.filme.getTitulo() + " " + sdf.format(i.filme.getData())
                            + " "
                            + i.filme.getDuracao() + " " + i.filme.getGenero() + " " + i.filme.getIdioma() + " "
                            + i.filme.getSituacao()
                            + " "
                            + i.filme.getOrcamento() + " ");

            // IMPRIMIR ARRAY PALAVRAS CHAVE
            System.out.print("[");
            if (i.filme.getPalavrasChave() != null) {
                String[] pChave = i.filme.getPalavrasChave();

                for (int k = 0; k < pChave.length - 1; k++) {
                    System.out.print(pChave[k] + ", ");
                }
                System.out.print(pChave[pChave.length - 1]);
            }
            System.out.print("]\n");
        }
    }

    public int tamanho() {
        int tamanho = 0;
        for (Celula i = primeiro; i != ultimo; i = i.prox, tamanho++);
        return tamanho;
    }
}