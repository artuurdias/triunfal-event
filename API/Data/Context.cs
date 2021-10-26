using Microsoft.EntityFrameworkCore;
using API.Models;

namespace API.Data
{
    public class Context: DbContext
    {
        public Context(DbContextOptions<Context> options): base (options)
        { }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Convidado>().HasKey(c => new {
                c.nomeUsuario, c.idEvento
            });
            modelBuilder.Entity<Convite>().HasKey(c => new {
                c.nomeUsuario, c.idEvento 
            });
            modelBuilder.Entity<Dica>().HasKey(d => new {
                d.tipoEvento, d.conteudo
            });
            modelBuilder.Entity<Lembrete>().HasKey(l => new {
                l.usuario, l.data
            });
        }

        public DbSet<Usuario> Usuario { get; set; }
        public DbSet<Evento> Evento { get; set; }
        public DbSet<Convidado> Convidado { get; set; }
        public DbSet<Dica> Dica { get; set; }
        public DbSet<Lembrete> Lembrete { get; set; }
        public DbSet<Feriado> Feriado { get; set; }
        public DbSet<Convite> Convite { get; set; }
    }
}
