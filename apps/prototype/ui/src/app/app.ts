import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { ApiStatusService } from './api-status.service';

type ConnectionState = 'loading' | 'connected' | 'error';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class App {
  private readonly apiStatusService = inject(ApiStatusService);

  protected readonly connectionState = signal<ConnectionState>('loading');
  protected readonly statusMessage = signal('Checking the API connection…');

  constructor() {
    this.checkConnection();
  }

  protected checkConnection(): void {
    this.connectionState.set('loading');
    this.statusMessage.set('Checking the API connection…');

    this.apiStatusService.getStatus().subscribe({
      next: (message) => {
        this.statusMessage.set(message);
        this.connectionState.set('connected');
      },
      error: () => {
        this.statusMessage.set('The API could not be reached.');
        this.connectionState.set('error');
      },
    });
  }
}
