const express = require('express');	
const { createProxyMiddleware } = require('http-proxy-middleware');
var cors = require('cors');

const app = express();
app.use(cors());

app.use('/financeiro', createProxyMiddleware(
    { 
        target: 'http://localhost:5001', 
        changeOrigin: true,
        pathRewrite: {
            '^/financeiro' : ''
        }
    }   
));

app.use('/cadastro', createProxyMiddleware(
    { 
        target: 'http://localhost:5002', 
        changeOrigin: true,
        pathRewrite: {
            '^/cadastro' : ''
        }
    }
));

// Não faz parte oficialmente do ERP, mas está aqui para facilitar os utilitários de teste/homologação da POC.
// Para testes locais, a chave é setada a cada request.
app.use('/integracao', createProxyMiddleware(
    { 
        target: 'http://localhost:5003', 
        changeOrigin: true,
        pathRewrite: {
            '^/integracao' : ''
        },
	    onProxyReq: (pr, req, res) => {
			   pr.setHeader('Authorization', 'Bearer b843de44e8ef7a67974ecfeefed6479eda4a84bf');
		},
    }
));

app.listen(5000);