using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using API.Models;
using API.Data;

namespace API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class EventoController : Controller
    {

        string dia , mes, ano = null;

        private readonly Context _context;
        public EventoController(Context context)
        {
            // construtor
            _context = context;
        }

        [HttpGet]
        public ActionResult<List<Evento>>  GetAll()
        {
            return _context.Evento.ToList();
        }

        [HttpGet("{id}")]
        public ActionResult<Evento> GetById(string id)
        {
            try
            {
		        var events = _context.Evento.Find(id);
                return events;
            }
            catch 
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

                
        [HttpGet("user/{username}")]
        public ActionResult<List<Evento>> GetByIdUsername(string username)
        {
            try
            {

                var convites    = _context.Convidado.ToList().FindAll(i => i.nomeUsuario == username);

                List<Evento> ret    = new List<Evento>(convites.Count);

                Evento evento = null;

                for(int i = 0; i < convites.Count; i++)
                {
                    evento = _context.Evento.Find(convites[i].idEvento);
                    limpaData(evento.data);
                    DateTime dataEvento = new DateTime(int.Parse(ano),int.Parse(mes),int.Parse(dia));
                    DateTime dataAtual = DateTime.Now;
                    int result = DateTime.Compare(dataAtual, dataEvento);
                    if(!ret.Contains(evento) && result <= 0)
                        ret.Add(evento);
                }

                return ret;
            }
            catch 
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpGet("org/{username}")]
        public ActionResult<List<Evento>> GetByIdOrganizador(string username)
        {
            try
            {
                var eventos = _context.Evento.ToList().FindAll(i => i.organizador == username);
                
                List<Evento> ret    = new List<Evento>(eventos.Count);

                for(int i = 0; i < eventos.Count; i++)
                {
                    limpaData(eventos[i].data);
                    DateTime dataEvento = new DateTime(int.Parse(ano),int.Parse(mes),int.Parse(dia));
                    DateTime dataAtual = DateTime.Now;
                    int result = DateTime.Compare(dataAtual, dataEvento);
                    if(result <= 0)
                        ret.Add(eventos[i]);
                }
                    

                return ret;
            }
            catch 
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpGet("part/{username}")]
        public ActionResult<List<Evento>> GetByParticipa(string username)
        {
            try
            {

                var convites    = _context.Convidado.ToList().FindAll(i => i.nomeUsuario == username);


                var eventos =  _context.Evento.ToList().FindAll(i => i.organizador == username);

                
                int qtd = convites.Count+eventos.Count;

                List<Evento> ret    = new List<Evento>(qtd);

                Evento evento = null;                

                for(int i = 0; i < convites.Count; i++)
                {
                    evento = _context.Evento.Find(convites[i].idEvento);
                    limpaData(evento.data);
                    DateTime dataEvento = new DateTime(int.Parse(ano),int.Parse(mes),int.Parse(dia));
                    DateTime dataAtual = DateTime.Now;
                    int result = DateTime.Compare(dataAtual, dataEvento);
                    if(!ret.Contains(evento) && result <= 0)
                        ret.Add(evento);
                    
                }

                for(int i = 0; i < eventos.Count; i++)
                {
                    limpaData(eventos[i].data);
                    DateTime dataEvento = new DateTime(int.Parse(ano),int.Parse(mes),int.Parse(dia));
                    DateTime dataAtual = DateTime.Now;
                    int result = DateTime.Compare(dataAtual, dataEvento);
                    if(!ret.Contains(eventos[i]) && result <= 0)
                        ret.Add(eventos[i]);
                }

                return ret;            
            }
            catch 
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpGet("pass/{username}")]
        public ActionResult<List<Evento>> GetByPassados(string username)
        {
            try
            {

                var convites    = _context.Convidado.ToList().FindAll(i => i.nomeUsuario == username);

                var eventos =  _context.Evento.ToList().FindAll(i => i.organizador == username);
                
                int qtd = convites.Count+eventos.Count;

                List<Evento> ret    = new List<Evento>(qtd);

                Evento evento = null;
                
                for(int i = 0; i < convites.Count; i++)
                {
                    evento = _context.Evento.Find(convites[i].idEvento);
                    limpaData(evento.data);
                    DateTime dataEvento = new DateTime(int.Parse(ano),int.Parse(mes),int.Parse(dia));
                    DateTime dataAtual = DateTime.Now;
                    int result = DateTime.Compare(dataAtual, dataEvento);
                    if(!ret.Contains(evento) && result > 0)
                        ret.Add(evento);
                    
                }

                for(int i = 0; i < eventos.Count; i++)
                {
                    limpaData(eventos[i].data);
                    DateTime dataEvento = new DateTime(int.Parse(ano),int.Parse(mes),int.Parse(dia));
                    DateTime dataAtual = DateTime.Now;
                    int result = DateTime.Compare(dataAtual, dataEvento);
                    if(!ret.Contains(eventos[i]) && result > 0)
                        ret.Add(eventos[i]);
                }

                return ret;            
            }
            catch 
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        public void limpaData(string data) 
        {
             dia = data.Substring(0, 2);
             mes = data.Substring(dia.Length, 3);
             ano = data.Substring(4, data.Length - 4);
            for (byte i = 0; i < dia.Length; i++)
                if (dia.Substring(i, 1) == "/")
                    dia = dia.Substring(i - 1, 1);
            for (byte i = 0; i < mes.Length; i++)
            {
                if (mes.Substring(1, 1) == "/")
                {
                    mes = mes.Substring(0, 1);
                    break;
                }
                if (mes.Substring(i, 1) == "/")
                    mes = mes.Replace('/', ' ');
            }

            for (byte i = 0; i < ano.Length; i++)
            {
                if (ano.Substring(i, 1) == "/")
                { 
                    ano = ano.Remove(i, 1);
                    if(i !=0)
                        ano = ano.Remove(i - 1, 1);
                }

            }
            dia = dia.Trim();
            mes = mes.Trim();
            ano = ano.Trim();
        }

        [HttpGet("disp/{username}")]
       public ActionResult<List<Evento>> GetDisponiblesEvents(string username)
        {
            var eventos = _context.Evento.ToList();
            var convidados = _context.Convidado.ToList();
            Evento[] eventos1 = new Evento[eventos.Count + convidados.Count];
            int cont = 0;
            bool achou = false;

            for (int k = 0; k < eventos.Count; k++) 
            {
                limpaData(eventos[k].data);
                DateTime dataEvento = new DateTime(int.Parse(ano),int.Parse(mes),int.Parse(dia));
                DateTime dataAtual = DateTime.Now;

                int result = DateTime.Compare(dataAtual, dataEvento);
                if (eventos[k].organizador != username && result <= 0 )
                {
                    var conv = _context.Convidado.ToList().FindAll(c => c.idEvento == eventos[k].id);
                    for (int j = 0; j < conv.Count; j++)
                    {
                        if (conv[j].nomeUsuario == username)
                        {
                            achou = true;
                            break;
                        }
                    }
                    
                    if(achou == false)
                        eventos1[cont++] = eventos[k];
                }
            }

            Evento[] final = new Evento[cont];
            for (int i = 0; i < cont; i++)
                final[i] = eventos1[i];

            return final.ToList();
        }



        [HttpPost]
        public async Task<ActionResult> PostEvent(Evento evento)
        {
            try
            {
                _context.Evento.Add(evento);

                if (await _context.SaveChangesAsync() == 1)
                    return Created($"/api/evento/{evento.id}", evento);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
            return BadRequest();
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(string id)
        {
            try
            {
                var evento = await _context.Evento.FindAsync(id);

                if (evento == null)
                    return NotFound();

                _context.Remove(evento);
                await _context.SaveChangesAsync();

                return NoContent();
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> Put(string id, Evento evento)
        {
            try
            {
                var result = await _context.Evento.FindAsync(id);
                if (id != result.id)
                {
                    return BadRequest();
                }
                result.id = evento.id;
                result.nome = evento.nome;
                result.tipo = evento.tipo;
                result.data = evento.data;
                result.endereco = evento.endereco;
                result.organizador = evento.organizador;
                
                await _context.SaveChangesAsync();
                return NoContent();
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }
    }
}
