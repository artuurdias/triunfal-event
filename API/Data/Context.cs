using Microsoft.EntityFrameworkCore;
using API.Models;

namespace API.Data
{
    public class Context: DbContext
    {
        public Context(DbContextOptions<Context> options): base (options)
        { }

        public DbSet<Usuario> Usuario { get; set; }
        public DbSet<Evento> Evento { get; set; }
        public DbSet<Organizador> Organizador { get; set; }
        public DbSet<Convidado> Convidado { get; set; }
        public DbSet<Dica> Dica { get; set; }
        public DbSet<Lembrete> Lembrete { get; set; }
        public DbSet<Feriado> Feriado { get; set; }
        public DbSet<Convite> Convite { get; set; }
    }
}