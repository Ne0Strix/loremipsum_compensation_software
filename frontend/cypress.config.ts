import { defineConfig } from 'cypress';

const { execSync } = require('child_process');

export default defineConfig({
  e2e: {
    setupNodeEvents(on, config) {
      on('before:run', (details) => {
        // Code that needs to run before all specs
        console.log('Stopping all services...');
        execSync('docker compose down', { stdio: 'inherit' });

        console.log('Starting all services...');
        execSync('docker compose up --build -d', { stdio: 'inherit' });

        // Wait for a specific service to be ready
        console.log('Waiting for services to be ready...');
        execSync(
          'until curl --output /dev/null --silent --head --fail http://localhost:4200; do sleep 1; done',
          { stdio: 'inherit' }
        );
      });
      on('after:run', () => {
        console.log('Stopping all services after tests...');
        execSync('docker compose down', { stdio: 'inherit' });
      });
    },
  },

  component: {
    devServer: {
      framework: 'angular',
      bundler: 'webpack',
    },
    specPattern: '**/*.cy.ts',
  },
});
