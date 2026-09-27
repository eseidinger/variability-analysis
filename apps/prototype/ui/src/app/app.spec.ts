import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { App } from './app';

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();
  });

  afterEach(() => {
    TestBed.inject(HttpTestingController).verify();
  });

  it('should display a successful API connection', () => {
    const fixture = TestBed.createComponent(App);
    const request = TestBed.inject(HttpTestingController).expectOne('/api/status');

    expect(request.request.method).toBe('GET');
    request.flush('Variability Analysis API is reachable');
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Variability Analysis');
    expect(compiled.querySelector('[role="status"]')?.textContent).toContain(
      'Variability Analysis API is reachable',
    );
  });

  it('should display an API connection error', () => {
    const fixture = TestBed.createComponent(App);
    const request = TestBed.inject(HttpTestingController).expectOne('/api/status');

    request.flush('Unavailable', { status: 503, statusText: 'Service Unavailable' });
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('[role="status"]')?.textContent).toContain(
      'The API could not be reached.',
    );
  });
});
