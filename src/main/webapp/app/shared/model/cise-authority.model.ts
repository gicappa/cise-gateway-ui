import { ICiseService } from 'app/shared/model//cise-service.model';
import { ICiseRuleSet } from 'app/shared/model//cise-rule-set.model';

export interface ICiseAuthority {
  id?: number;
  name?: string;
  description?: string;
  ciseServices?: ICiseService[];
  ciseRuleSets?: ICiseRuleSet[];
  userId?: number;
}

export const defaultValue: Readonly<ICiseAuthority> = {};
