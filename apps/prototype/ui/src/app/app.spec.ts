import { provideHttpClient } from "@angular/common/http";
import { HttpTestingController, provideHttpClientTesting } from "@angular/common/http/testing";
import { TestBed } from "@angular/core/testing";
import { FoodServiceDashboardComponent } from "./food-service-dashboard";

const metrics = { recordCount: 12, variantCount: 11, uniqueElementCount: 24, elementRecordCounts: {}, elementRecordFrequencies: {} };

describe("FoodServiceDashboardComponent", () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FoodServiceDashboardComponent],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();
  });

  afterEach(() => TestBed.inject(HttpTestingController).verify());

  it("shows population metrics after loading the summary", () => {
    const fixture = TestBed.createComponent(FoodServiceDashboardComponent);
    const http = TestBed.inject(HttpTestingController);
    http.expectOne("/api/populations/food-service-mvp").flush({
      population: { id: "food-service-mvp", version: "1.0.0", provenance: {} },
      dimensions: [], metrics, rejectedRecords: [{ sourceRowId: "13", reason: "missing ingredients" }],
    });
    http.expectOne("/api/populations/food-service-mvp/analysis").flush({
      population: { id: "food-service-mvp", version: "1.0.0", provenance: {} }, query: {}, selectedRecordIds: [], metrics, groups: [],
    });
    fixture.detectChanges();

    expect((fixture.nativeElement as HTMLElement).textContent).toContain("Accepted recipes");
    expect((fixture.nativeElement as HTMLElement).textContent).toContain("12");
  });

  it("explains when the population cannot be loaded", () => {
    const fixture = TestBed.createComponent(FoodServiceDashboardComponent);
    TestBed.inject(HttpTestingController).expectOne("/api/populations/food-service-mvp").flush("Unavailable", { status: 503, statusText: "Unavailable" });
    fixture.detectChanges();

    expect((fixture.nativeElement as HTMLElement).querySelector("[role=alert]")?.textContent).toContain("could not be loaded");
  });
});
