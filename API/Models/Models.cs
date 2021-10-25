using System.ComponentModel.DataAnnotations;

namespace API.Models
{
    public class Usuario
    {
        [Key]
        public string username { get; set; }
        public string nome { get; set; }
        public string nascimento { get; set; }
        public string email { get; set; }
        public string senha { get; set; }
    }

    public class Evento
    {
        [Key]
        public string id { get; set; }
        public string nome { get; set; }
        public string tipo { get; set; }
        public string data { get; set; }
        public string local { get; set; }
    }

    public class Convidado 
    {
        [Key]
        public string nomeUsuario { get; set; }
        public string idEvento { get; set; }
    }

    public class Dica
    {
        [Key]
        public int id { get; set; }
        public string tipoEvento { get; set; }
        public string conteudo { get; set; }
    }

    public class Lembrete
    {
        [Key]
        public int id { get; set; }
        public string nome { get; set; }
        public string data { get; set; }
        public string local { get; set; }
    }

    public class Feriado
    {
        [Key]
        public int id { get; set; }
        public string nome { get; set; }
        public string data { get; set; }
    }

    public class Convite
    {
        [Key]
        public int      id          { get; set; }
        public string   data        { get; set; }
        public string   mensagem    { get; set; }
        public string   idEvento    { get; set; }
        public string   nomeUsuario { get; set; }
    }
}