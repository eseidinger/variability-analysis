import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ApiStatusService {
  private readonly http = inject(HttpClient);

  getStatus(): Observable<string> {
    return this.http.get('/api/status', { responseType: 'text' });
  }
}
