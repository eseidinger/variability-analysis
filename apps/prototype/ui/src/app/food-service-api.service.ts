import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

export type DimensionId = 'cuisine' | 'diet' | 'dishType';

export interface Metrics {
  recordCount: number;
  variantCount: number;
  uniqueElementCount: number;
  elementRecordCounts: Record<string, number>;
  elementRecordFrequencies: Record<string, number>;
}

export interface PopulationReference {
  id: string;
  version: string;
  provenance: Record<string, string>;
}

export interface PopulationSummary {
  population: PopulationReference;
  dimensions: Array<{ id: DimensionId; displayName: string }>;
  metrics: Metrics;
  rejectedRecords: Array<{ sourceRowId: string; reason: string }>;
}

export interface DimensionFilter {
  requiredState?: 'KNOWN' | 'UNKNOWN';
  includedValues?: string[];
  excludedValues?: string[];
}

export interface AnalysisRequest {
  dimensionFilters?: Partial<Record<DimensionId, DimensionFilter>>;
  requiredElementIds?: string[];
  groupingDimensions?: DimensionId[];
}

export interface AnalysisGroup {
  path: Array<{ dimensionId: DimensionId; valueKind: 'VALUE' | 'KNOWN_EMPTY' | 'UNKNOWN'; value?: string }>;
  metrics: Metrics;
  children: AnalysisGroup[];
}

export interface AnalysisResponse {
  population: PopulationReference;
  query: AnalysisRequest;
  selectedRecordIds: string[];
  metrics: Metrics;
  groups: AnalysisGroup[];
}

export interface LeverageResponse {
  population: PopulationReference;
  query: AnalysisRequest;
  elementId: string;
  recordCount: number;
  knownDimensionValues: Partial<Record<DimensionId, string[]>>;
}

export interface UnavailabilityResponse {
  population: PopulationReference;
  query: AnalysisRequest;
  scenario: { unavailableElementIds: string[] };
  baseline: AnalysisResponse;
  scenarioResult: AnalysisResponse;
  lostRecordIds: string[];
  lostElementIds: string[];
  rootDelta: Pick<Metrics, 'recordCount' | 'variantCount' | 'uniqueElementCount'>;
}

@Injectable({ providedIn: 'root' })
export class FoodServiceApiService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = '/api/populations/food-service-mvp';

  summary(): Observable<PopulationSummary> {
    return this.http.get<PopulationSummary>(this.baseUrl);
  }

  analyse(query: AnalysisRequest): Observable<AnalysisResponse> {
    return this.http.post<AnalysisResponse>(`${this.baseUrl}/analysis`, query);
  }

  leverage(elementId: string, query: AnalysisRequest): Observable<LeverageResponse> {
    return this.http.post<LeverageResponse>(`${this.baseUrl}/elements/${encodeURIComponent(elementId)}/leverage`, {
      query,
      dimensionIds: ['cuisine', 'diet'],
    });
  }

  unavailability(elementId: string, query: AnalysisRequest): Observable<UnavailabilityResponse> {
    return this.http.post<UnavailabilityResponse>(`${this.baseUrl}/element-unavailability`, {
      query,
      unavailableElementIds: [elementId],
    });
  }
}
