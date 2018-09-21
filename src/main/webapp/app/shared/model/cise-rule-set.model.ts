import { ICiseRule } from 'app/shared/model//cise-rule.model';

export interface ICiseRuleSet {
  id?: number;
  name?: string;
  description?: string;
  ciseRules?: ICiseRule[];
  ciseServiceId?: number;
  ciseAuthorityId?: number;
}

export const defaultValue: Readonly<ICiseRuleSet> = {};
