import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const invalidDomainValidator = (): ValidatorFn => {
  return (control: AbstractControl): ValidationErrors | null => {
    const parts = control.value.split('@', 2);
    const invalid = parts.length === 2 && parts[1] !== 'dsomorov.xyz';
    return invalid ? { invalidDomain: true } : null;
  };
};
