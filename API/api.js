//inicializa o Express
let express = require('express')
let app = express()
var cors = require('cors');
var bodyParser = require('body-parser');

let porta = 8081

app.listen(porta)
console.log('A API está no ar')

//Setando o middleware para usar json
//A função use server para registrar um middleware no Express
app.use(express.json())
app.use(express.urlencoded({ extended: true }))
app.use(cors());


let data = [
    {
        "id": "1",
        "nome": "Pipoca 2",
        "image": "https://i.pinimg.com/originals/b2/21/5c/b2215c404b08d0f0905d693ca55d1708.jpg",
        "raca": "York Shire"
    },
    {
        "id": "2",
        "nome": "Rick",
        "image": "https://upload.wikimedia.org/wikipedia/commons/2/27/2._DSC_0346_%2810096362833%292.jpg",
        "raca": "Pastor Alemão"
    },
    {
        "id": "3",
        "nome": "Lexy",
        "image": "https://www.universodoaquario.com.br/image/cache/catalog/filhotes/cao-raca-maltes-900x900.jpeg",
        "raca": "Maltês"
    },
    {
        "id": "4",
        "nome": "Gigi",
        "image": "https://i.pinimg.com/originals/4e/e6/d5/4ee6d5eb4079c9db7dc17074d3073c23.jpg",
        "raca": "Schnowser"
    }
]


app.get('/api/dog/get', (req, res) => {
    setTimeout(function () {
        res.header('Access-Control-Allow-Origin', '*')
            .send(200, data

            )
    }, 3000);
});


app.get('/api/dog/getNome/:nome', (req, res) => {
    const nome = req.params.nome;
    const item = data.find(item => item.nome == nome);
    console.log(item)
    return res.json({ item })
})

app.post('/api/dog/post', (req, res) => {
    const result = req.body;
    data.push(result)
    return res.json({ data })

})

app.put('/api/dog/put/:id', (req, res) => {
    const id = req.params.id;
    const item = data.find(item => item.id == id);

    item.nome = req.body.nome;
    item.raca = req.body.raca;
    item.image = req.body.image;

    return res.json({ data });
});

app.delete('/api/dog/delete/:id', (req, res) => {
    const id = req.params.id;
    const index = data.findIndex(item => item.id == id);
    data.splice(index, 1)
    console.log(data)

    return res.json({ data })
})