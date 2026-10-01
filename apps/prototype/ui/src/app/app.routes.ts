import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./food-service-dashboard').then((module) => module.FoodServiceDashboardComponent),
  },
  { path: '**', redirectTo: '' },
];
