import { IPessoa } from 'app/shared/model/pessoa.model';
import { IResponsavel } from 'app/shared/model/responsavel.model';

export interface ITelefone {
  id?: string;
  numero?: string;
  whatsapp?: boolean;
  pessoa?: IPessoa | null;
  responsavel?: IResponsavel | null;
}

export const defaultValue: Readonly<ITelefone> = {
  whatsapp: false,
};
