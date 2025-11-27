// Mock API server using json-server
// Serves ERP and Tax APIs under namespaced paths
const jsonServer = require('json-server');
const path = require('path');

const server = jsonServer.create();
const middlewares = jsonServer.defaults({
  logger: true,
  noCors: false
});

server.use(middlewares);
server.use(jsonServer.bodyParser);

// ERP subsystem health check
server.get('/erp/health', (req, res) => {
  res.status(200).json({
    status: 'UP',
    subsystem: 'ERP',
    timestamp: new Date().toISOString()
  });
});

// Tax subsystem health check
server.get('/tax/health', (req, res) => {
  res.status(200).json({
    status: 'UP',
    subsystem: 'Tax',
    timestamp: new Date().toISOString()
  });
});

// Mount ERP API at /erp/api with custom rewriter
const erpRouter = jsonServer.router(path.join(__dirname, 'json-server-db.erp-api.json'));
server.get('/erp/api', (req, res) => {
  res.status(200).json({
    message: 'ERP API',
    endpoints: ['/erp/api/products']
  });
});
server.use('/erp/api', erpRouter);

// Mount Tax API at /tax/api with custom rewriter
const taxRouter = jsonServer.router(path.join(__dirname, 'json-server-db.tax-api.json'));
server.get('/tax/api', (req, res) => {
  res.status(200).json({
    message: 'Tax API',
    endpoints: ['/tax/api/countries']
  });
});
server.use('/tax/api', taxRouter);

const port = process.env.PORT || 9000;
server.listen(port, () => {
  console.log(`Mock API Server running on http://localhost:${port}`);
  console.log(`ERP Health: http://localhost:${port}/erp/health`);
  console.log(`ERP API: http://localhost:${port}/erp/api/products`);
  console.log(`Tax Health: http://localhost:${port}/tax/health`);
  console.log(`Tax API: http://localhost:${port}/tax/api/countries`);
});

