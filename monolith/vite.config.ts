import { defineConfig } from 'vite';
import { resolve } from 'path';

export default defineConfig({
  root: 'src/frontend',
  build: {
    outDir: '../../src/main/resources/static/dist',
    emptyOutDir: true,
    rollupOptions: {
      input: {
        'place-order': resolve(__dirname, 'src/frontend/pages/place-order.ts'),
        'order-history': resolve(__dirname, 'src/frontend/pages/order-history.ts'),
      },
      output: {
        entryFileNames: 'js/[name].js',
        chunkFileNames: 'js/[name].js',
        assetFileNames: 'assets/[name].[ext]'
      }
    }
  },
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
});

