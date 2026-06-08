package main.game.maze.dsl.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import main.game.maze.dsl.services.MazeDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMazeDslParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_INT", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'true'", "'false'", "'easy'", "'normal'", "'hard'", "'zombie'", "'ghost'", "'pumpkinbomber'", "'passive'", "'wander'", "'aggressive'", "'patrol'", "'straight'", "'lob'", "'beam'", "'food'", "'bomb'", "'trap'", "'weapon'", "'game'", "'{'", "'}'", "'import'", "'difficulty'", "'level'", "'instantDeath'", "'speedMultiplier'", "'damageMultiplier'", "'maxThreat'", "'limit'", "'max'", "'opponent'", "'type'", "'displayName'", "'health'", "'speed'", "'threatLevel'", "'enabled'", "'behavior'", "'loot'", "'zombie-stats'", "'attackDamage'", "'infectionLevel'", "'resurrectionTime'", "'ghost-stats'", "'visibilityLevel'", "'nonTangibilityEnergy'", "'ranged-stats'", "'attackRange'", "'attackCooldown'", "'projectileSpeed'", "'projectileType'", "'splashRadius'", "'path'", "'['", "']'", "'visionRange'", "','", "'zone'", "'topLeft'", "'('", "')'", "'width'", "'height'", "':'", "'ms'", "'loot-table'", "'capacity'", "'item'", "'value'", "'weight'", "'-'", "'.'"
    };
    public static final int T__50=50;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__59=59;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__55=55;
    public static final int T__12=12;
    public static final int T__56=56;
    public static final int T__13=13;
    public static final int T__57=57;
    public static final int T__14=14;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=5;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=4;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__67=67;
    public static final int T__24=24;
    public static final int T__68=68;
    public static final int T__25=25;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__20=20;
    public static final int T__64=64;
    public static final int T__21=21;
    public static final int T__65=65;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_STRING=6;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__77=77;
    public static final int T__34=34;
    public static final int T__78=78;
    public static final int T__35=35;
    public static final int T__79=79;
    public static final int T__36=36;
    public static final int T__73=73;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__74=74;
    public static final int T__31=31;
    public static final int T__75=75;
    public static final int T__32=32;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;

    // delegates
    // delegators


        public InternalMazeDslParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalMazeDslParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalMazeDslParser.tokenNames; }
    public String getGrammarFileName() { return "InternalMazeDsl.g"; }


    	private MazeDslGrammarAccess grammarAccess;

    	public void setGrammarAccess(MazeDslGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleGameConfiguration"
    // InternalMazeDsl.g:53:1: entryRuleGameConfiguration : ruleGameConfiguration EOF ;
    public final void entryRuleGameConfiguration() throws RecognitionException {
        try {
            // InternalMazeDsl.g:54:1: ( ruleGameConfiguration EOF )
            // InternalMazeDsl.g:55:1: ruleGameConfiguration EOF
            {
             before(grammarAccess.getGameConfigurationRule()); 
            pushFollow(FOLLOW_1);
            ruleGameConfiguration();

            state._fsp--;

             after(grammarAccess.getGameConfigurationRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleGameConfiguration"


    // $ANTLR start "ruleGameConfiguration"
    // InternalMazeDsl.g:62:1: ruleGameConfiguration : ( ( rule__GameConfiguration__Group__0 ) ) ;
    public final void ruleGameConfiguration() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:66:2: ( ( ( rule__GameConfiguration__Group__0 ) ) )
            // InternalMazeDsl.g:67:2: ( ( rule__GameConfiguration__Group__0 ) )
            {
            // InternalMazeDsl.g:67:2: ( ( rule__GameConfiguration__Group__0 ) )
            // InternalMazeDsl.g:68:3: ( rule__GameConfiguration__Group__0 )
            {
             before(grammarAccess.getGameConfigurationAccess().getGroup()); 
            // InternalMazeDsl.g:69:3: ( rule__GameConfiguration__Group__0 )
            // InternalMazeDsl.g:69:4: rule__GameConfiguration__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGameConfigurationAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleGameConfiguration"


    // $ANTLR start "entryRuleImport"
    // InternalMazeDsl.g:78:1: entryRuleImport : ruleImport EOF ;
    public final void entryRuleImport() throws RecognitionException {
        try {
            // InternalMazeDsl.g:79:1: ( ruleImport EOF )
            // InternalMazeDsl.g:80:1: ruleImport EOF
            {
             before(grammarAccess.getImportRule()); 
            pushFollow(FOLLOW_1);
            ruleImport();

            state._fsp--;

             after(grammarAccess.getImportRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleImport"


    // $ANTLR start "ruleImport"
    // InternalMazeDsl.g:87:1: ruleImport : ( ( rule__Import__Group__0 ) ) ;
    public final void ruleImport() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:91:2: ( ( ( rule__Import__Group__0 ) ) )
            // InternalMazeDsl.g:92:2: ( ( rule__Import__Group__0 ) )
            {
            // InternalMazeDsl.g:92:2: ( ( rule__Import__Group__0 ) )
            // InternalMazeDsl.g:93:3: ( rule__Import__Group__0 )
            {
             before(grammarAccess.getImportAccess().getGroup()); 
            // InternalMazeDsl.g:94:3: ( rule__Import__Group__0 )
            // InternalMazeDsl.g:94:4: rule__Import__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Import__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getImportAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleImport"


    // $ANTLR start "entryRuleDifficultyConfig"
    // InternalMazeDsl.g:103:1: entryRuleDifficultyConfig : ruleDifficultyConfig EOF ;
    public final void entryRuleDifficultyConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:104:1: ( ruleDifficultyConfig EOF )
            // InternalMazeDsl.g:105:1: ruleDifficultyConfig EOF
            {
             before(grammarAccess.getDifficultyConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleDifficultyConfig();

            state._fsp--;

             after(grammarAccess.getDifficultyConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDifficultyConfig"


    // $ANTLR start "ruleDifficultyConfig"
    // InternalMazeDsl.g:112:1: ruleDifficultyConfig : ( ( rule__DifficultyConfig__Group__0 ) ) ;
    public final void ruleDifficultyConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:116:2: ( ( ( rule__DifficultyConfig__Group__0 ) ) )
            // InternalMazeDsl.g:117:2: ( ( rule__DifficultyConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:117:2: ( ( rule__DifficultyConfig__Group__0 ) )
            // InternalMazeDsl.g:118:3: ( rule__DifficultyConfig__Group__0 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup()); 
            // InternalMazeDsl.g:119:3: ( rule__DifficultyConfig__Group__0 )
            // InternalMazeDsl.g:119:4: rule__DifficultyConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDifficultyConfig"


    // $ANTLR start "entryRuleEnemyLimit"
    // InternalMazeDsl.g:128:1: entryRuleEnemyLimit : ruleEnemyLimit EOF ;
    public final void entryRuleEnemyLimit() throws RecognitionException {
        try {
            // InternalMazeDsl.g:129:1: ( ruleEnemyLimit EOF )
            // InternalMazeDsl.g:130:1: ruleEnemyLimit EOF
            {
             before(grammarAccess.getEnemyLimitRule()); 
            pushFollow(FOLLOW_1);
            ruleEnemyLimit();

            state._fsp--;

             after(grammarAccess.getEnemyLimitRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleEnemyLimit"


    // $ANTLR start "ruleEnemyLimit"
    // InternalMazeDsl.g:137:1: ruleEnemyLimit : ( ( rule__EnemyLimit__Group__0 ) ) ;
    public final void ruleEnemyLimit() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:141:2: ( ( ( rule__EnemyLimit__Group__0 ) ) )
            // InternalMazeDsl.g:142:2: ( ( rule__EnemyLimit__Group__0 ) )
            {
            // InternalMazeDsl.g:142:2: ( ( rule__EnemyLimit__Group__0 ) )
            // InternalMazeDsl.g:143:3: ( rule__EnemyLimit__Group__0 )
            {
             before(grammarAccess.getEnemyLimitAccess().getGroup()); 
            // InternalMazeDsl.g:144:3: ( rule__EnemyLimit__Group__0 )
            // InternalMazeDsl.g:144:4: rule__EnemyLimit__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__EnemyLimit__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getEnemyLimitAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEnemyLimit"


    // $ANTLR start "entryRuleOpponentConfig"
    // InternalMazeDsl.g:153:1: entryRuleOpponentConfig : ruleOpponentConfig EOF ;
    public final void entryRuleOpponentConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:154:1: ( ruleOpponentConfig EOF )
            // InternalMazeDsl.g:155:1: ruleOpponentConfig EOF
            {
             before(grammarAccess.getOpponentConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleOpponentConfig();

            state._fsp--;

             after(grammarAccess.getOpponentConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleOpponentConfig"


    // $ANTLR start "ruleOpponentConfig"
    // InternalMazeDsl.g:162:1: ruleOpponentConfig : ( ( rule__OpponentConfig__Group__0 ) ) ;
    public final void ruleOpponentConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:166:2: ( ( ( rule__OpponentConfig__Group__0 ) ) )
            // InternalMazeDsl.g:167:2: ( ( rule__OpponentConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:167:2: ( ( rule__OpponentConfig__Group__0 ) )
            // InternalMazeDsl.g:168:3: ( rule__OpponentConfig__Group__0 )
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup()); 
            // InternalMazeDsl.g:169:3: ( rule__OpponentConfig__Group__0 )
            // InternalMazeDsl.g:169:4: rule__OpponentConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleOpponentConfig"


    // $ANTLR start "entryRuleCharacterSpecifics"
    // InternalMazeDsl.g:178:1: entryRuleCharacterSpecifics : ruleCharacterSpecifics EOF ;
    public final void entryRuleCharacterSpecifics() throws RecognitionException {
        try {
            // InternalMazeDsl.g:179:1: ( ruleCharacterSpecifics EOF )
            // InternalMazeDsl.g:180:1: ruleCharacterSpecifics EOF
            {
             before(grammarAccess.getCharacterSpecificsRule()); 
            pushFollow(FOLLOW_1);
            ruleCharacterSpecifics();

            state._fsp--;

             after(grammarAccess.getCharacterSpecificsRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCharacterSpecifics"


    // $ANTLR start "ruleCharacterSpecifics"
    // InternalMazeDsl.g:187:1: ruleCharacterSpecifics : ( ( rule__CharacterSpecifics__Alternatives ) ) ;
    public final void ruleCharacterSpecifics() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:191:2: ( ( ( rule__CharacterSpecifics__Alternatives ) ) )
            // InternalMazeDsl.g:192:2: ( ( rule__CharacterSpecifics__Alternatives ) )
            {
            // InternalMazeDsl.g:192:2: ( ( rule__CharacterSpecifics__Alternatives ) )
            // InternalMazeDsl.g:193:3: ( rule__CharacterSpecifics__Alternatives )
            {
             before(grammarAccess.getCharacterSpecificsAccess().getAlternatives()); 
            // InternalMazeDsl.g:194:3: ( rule__CharacterSpecifics__Alternatives )
            // InternalMazeDsl.g:194:4: rule__CharacterSpecifics__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__CharacterSpecifics__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getCharacterSpecificsAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCharacterSpecifics"


    // $ANTLR start "entryRuleZombieSpecifics"
    // InternalMazeDsl.g:203:1: entryRuleZombieSpecifics : ruleZombieSpecifics EOF ;
    public final void entryRuleZombieSpecifics() throws RecognitionException {
        try {
            // InternalMazeDsl.g:204:1: ( ruleZombieSpecifics EOF )
            // InternalMazeDsl.g:205:1: ruleZombieSpecifics EOF
            {
             before(grammarAccess.getZombieSpecificsRule()); 
            pushFollow(FOLLOW_1);
            ruleZombieSpecifics();

            state._fsp--;

             after(grammarAccess.getZombieSpecificsRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleZombieSpecifics"


    // $ANTLR start "ruleZombieSpecifics"
    // InternalMazeDsl.g:212:1: ruleZombieSpecifics : ( ( rule__ZombieSpecifics__Group__0 ) ) ;
    public final void ruleZombieSpecifics() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:216:2: ( ( ( rule__ZombieSpecifics__Group__0 ) ) )
            // InternalMazeDsl.g:217:2: ( ( rule__ZombieSpecifics__Group__0 ) )
            {
            // InternalMazeDsl.g:217:2: ( ( rule__ZombieSpecifics__Group__0 ) )
            // InternalMazeDsl.g:218:3: ( rule__ZombieSpecifics__Group__0 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup()); 
            // InternalMazeDsl.g:219:3: ( rule__ZombieSpecifics__Group__0 )
            // InternalMazeDsl.g:219:4: rule__ZombieSpecifics__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getZombieSpecificsAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleZombieSpecifics"


    // $ANTLR start "entryRuleGhostSpecifics"
    // InternalMazeDsl.g:228:1: entryRuleGhostSpecifics : ruleGhostSpecifics EOF ;
    public final void entryRuleGhostSpecifics() throws RecognitionException {
        try {
            // InternalMazeDsl.g:229:1: ( ruleGhostSpecifics EOF )
            // InternalMazeDsl.g:230:1: ruleGhostSpecifics EOF
            {
             before(grammarAccess.getGhostSpecificsRule()); 
            pushFollow(FOLLOW_1);
            ruleGhostSpecifics();

            state._fsp--;

             after(grammarAccess.getGhostSpecificsRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleGhostSpecifics"


    // $ANTLR start "ruleGhostSpecifics"
    // InternalMazeDsl.g:237:1: ruleGhostSpecifics : ( ( rule__GhostSpecifics__Group__0 ) ) ;
    public final void ruleGhostSpecifics() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:241:2: ( ( ( rule__GhostSpecifics__Group__0 ) ) )
            // InternalMazeDsl.g:242:2: ( ( rule__GhostSpecifics__Group__0 ) )
            {
            // InternalMazeDsl.g:242:2: ( ( rule__GhostSpecifics__Group__0 ) )
            // InternalMazeDsl.g:243:3: ( rule__GhostSpecifics__Group__0 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup()); 
            // InternalMazeDsl.g:244:3: ( rule__GhostSpecifics__Group__0 )
            // InternalMazeDsl.g:244:4: rule__GhostSpecifics__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getGhostSpecificsAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleGhostSpecifics"


    // $ANTLR start "entryRuleRangedSpecifics"
    // InternalMazeDsl.g:253:1: entryRuleRangedSpecifics : ruleRangedSpecifics EOF ;
    public final void entryRuleRangedSpecifics() throws RecognitionException {
        try {
            // InternalMazeDsl.g:254:1: ( ruleRangedSpecifics EOF )
            // InternalMazeDsl.g:255:1: ruleRangedSpecifics EOF
            {
             before(grammarAccess.getRangedSpecificsRule()); 
            pushFollow(FOLLOW_1);
            ruleRangedSpecifics();

            state._fsp--;

             after(grammarAccess.getRangedSpecificsRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRangedSpecifics"


    // $ANTLR start "ruleRangedSpecifics"
    // InternalMazeDsl.g:262:1: ruleRangedSpecifics : ( ( rule__RangedSpecifics__Group__0 ) ) ;
    public final void ruleRangedSpecifics() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:266:2: ( ( ( rule__RangedSpecifics__Group__0 ) ) )
            // InternalMazeDsl.g:267:2: ( ( rule__RangedSpecifics__Group__0 ) )
            {
            // InternalMazeDsl.g:267:2: ( ( rule__RangedSpecifics__Group__0 ) )
            // InternalMazeDsl.g:268:3: ( rule__RangedSpecifics__Group__0 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup()); 
            // InternalMazeDsl.g:269:3: ( rule__RangedSpecifics__Group__0 )
            // InternalMazeDsl.g:269:4: rule__RangedSpecifics__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRangedSpecifics"


    // $ANTLR start "entryRulePatrolConfig"
    // InternalMazeDsl.g:278:1: entryRulePatrolConfig : rulePatrolConfig EOF ;
    public final void entryRulePatrolConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:279:1: ( rulePatrolConfig EOF )
            // InternalMazeDsl.g:280:1: rulePatrolConfig EOF
            {
             before(grammarAccess.getPatrolConfigRule()); 
            pushFollow(FOLLOW_1);
            rulePatrolConfig();

            state._fsp--;

             after(grammarAccess.getPatrolConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePatrolConfig"


    // $ANTLR start "rulePatrolConfig"
    // InternalMazeDsl.g:287:1: rulePatrolConfig : ( ( rule__PatrolConfig__Group__0 ) ) ;
    public final void rulePatrolConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:291:2: ( ( ( rule__PatrolConfig__Group__0 ) ) )
            // InternalMazeDsl.g:292:2: ( ( rule__PatrolConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:292:2: ( ( rule__PatrolConfig__Group__0 ) )
            // InternalMazeDsl.g:293:3: ( rule__PatrolConfig__Group__0 )
            {
             before(grammarAccess.getPatrolConfigAccess().getGroup()); 
            // InternalMazeDsl.g:294:3: ( rule__PatrolConfig__Group__0 )
            // InternalMazeDsl.g:294:4: rule__PatrolConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPatrolConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePatrolConfig"


    // $ANTLR start "entryRulePatrolZoneConfig"
    // InternalMazeDsl.g:303:1: entryRulePatrolZoneConfig : rulePatrolZoneConfig EOF ;
    public final void entryRulePatrolZoneConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:304:1: ( rulePatrolZoneConfig EOF )
            // InternalMazeDsl.g:305:1: rulePatrolZoneConfig EOF
            {
             before(grammarAccess.getPatrolZoneConfigRule()); 
            pushFollow(FOLLOW_1);
            rulePatrolZoneConfig();

            state._fsp--;

             after(grammarAccess.getPatrolZoneConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePatrolZoneConfig"


    // $ANTLR start "rulePatrolZoneConfig"
    // InternalMazeDsl.g:312:1: rulePatrolZoneConfig : ( ( rule__PatrolZoneConfig__Group__0 ) ) ;
    public final void rulePatrolZoneConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:316:2: ( ( ( rule__PatrolZoneConfig__Group__0 ) ) )
            // InternalMazeDsl.g:317:2: ( ( rule__PatrolZoneConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:317:2: ( ( rule__PatrolZoneConfig__Group__0 ) )
            // InternalMazeDsl.g:318:3: ( rule__PatrolZoneConfig__Group__0 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getGroup()); 
            // InternalMazeDsl.g:319:3: ( rule__PatrolZoneConfig__Group__0 )
            // InternalMazeDsl.g:319:4: rule__PatrolZoneConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPatrolZoneConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePatrolZoneConfig"


    // $ANTLR start "entryRuleWaypoint"
    // InternalMazeDsl.g:328:1: entryRuleWaypoint : ruleWaypoint EOF ;
    public final void entryRuleWaypoint() throws RecognitionException {
        try {
            // InternalMazeDsl.g:329:1: ( ruleWaypoint EOF )
            // InternalMazeDsl.g:330:1: ruleWaypoint EOF
            {
             before(grammarAccess.getWaypointRule()); 
            pushFollow(FOLLOW_1);
            ruleWaypoint();

            state._fsp--;

             after(grammarAccess.getWaypointRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleWaypoint"


    // $ANTLR start "ruleWaypoint"
    // InternalMazeDsl.g:337:1: ruleWaypoint : ( ( rule__Waypoint__Group__0 ) ) ;
    public final void ruleWaypoint() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:341:2: ( ( ( rule__Waypoint__Group__0 ) ) )
            // InternalMazeDsl.g:342:2: ( ( rule__Waypoint__Group__0 ) )
            {
            // InternalMazeDsl.g:342:2: ( ( rule__Waypoint__Group__0 ) )
            // InternalMazeDsl.g:343:3: ( rule__Waypoint__Group__0 )
            {
             before(grammarAccess.getWaypointAccess().getGroup()); 
            // InternalMazeDsl.g:344:3: ( rule__Waypoint__Group__0 )
            // InternalMazeDsl.g:344:4: rule__Waypoint__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getWaypointAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleWaypoint"


    // $ANTLR start "entryRuleLootTableConfig"
    // InternalMazeDsl.g:353:1: entryRuleLootTableConfig : ruleLootTableConfig EOF ;
    public final void entryRuleLootTableConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:354:1: ( ruleLootTableConfig EOF )
            // InternalMazeDsl.g:355:1: ruleLootTableConfig EOF
            {
             before(grammarAccess.getLootTableConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleLootTableConfig();

            state._fsp--;

             after(grammarAccess.getLootTableConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLootTableConfig"


    // $ANTLR start "ruleLootTableConfig"
    // InternalMazeDsl.g:362:1: ruleLootTableConfig : ( ( rule__LootTableConfig__Group__0 ) ) ;
    public final void ruleLootTableConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:366:2: ( ( ( rule__LootTableConfig__Group__0 ) ) )
            // InternalMazeDsl.g:367:2: ( ( rule__LootTableConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:367:2: ( ( rule__LootTableConfig__Group__0 ) )
            // InternalMazeDsl.g:368:3: ( rule__LootTableConfig__Group__0 )
            {
             before(grammarAccess.getLootTableConfigAccess().getGroup()); 
            // InternalMazeDsl.g:369:3: ( rule__LootTableConfig__Group__0 )
            // InternalMazeDsl.g:369:4: rule__LootTableConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLootTableConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLootTableConfig"


    // $ANTLR start "entryRuleLootItemConfig"
    // InternalMazeDsl.g:378:1: entryRuleLootItemConfig : ruleLootItemConfig EOF ;
    public final void entryRuleLootItemConfig() throws RecognitionException {
        try {
            // InternalMazeDsl.g:379:1: ( ruleLootItemConfig EOF )
            // InternalMazeDsl.g:380:1: ruleLootItemConfig EOF
            {
             before(grammarAccess.getLootItemConfigRule()); 
            pushFollow(FOLLOW_1);
            ruleLootItemConfig();

            state._fsp--;

             after(grammarAccess.getLootItemConfigRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLootItemConfig"


    // $ANTLR start "ruleLootItemConfig"
    // InternalMazeDsl.g:387:1: ruleLootItemConfig : ( ( rule__LootItemConfig__Group__0 ) ) ;
    public final void ruleLootItemConfig() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:391:2: ( ( ( rule__LootItemConfig__Group__0 ) ) )
            // InternalMazeDsl.g:392:2: ( ( rule__LootItemConfig__Group__0 ) )
            {
            // InternalMazeDsl.g:392:2: ( ( rule__LootItemConfig__Group__0 ) )
            // InternalMazeDsl.g:393:3: ( rule__LootItemConfig__Group__0 )
            {
             before(grammarAccess.getLootItemConfigAccess().getGroup()); 
            // InternalMazeDsl.g:394:3: ( rule__LootItemConfig__Group__0 )
            // InternalMazeDsl.g:394:4: rule__LootItemConfig__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLootItemConfigAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLootItemConfig"


    // $ANTLR start "entryRuleDOUBLE"
    // InternalMazeDsl.g:403:1: entryRuleDOUBLE : ruleDOUBLE EOF ;
    public final void entryRuleDOUBLE() throws RecognitionException {
        try {
            // InternalMazeDsl.g:404:1: ( ruleDOUBLE EOF )
            // InternalMazeDsl.g:405:1: ruleDOUBLE EOF
            {
             before(grammarAccess.getDOUBLERule()); 
            pushFollow(FOLLOW_1);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getDOUBLERule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDOUBLE"


    // $ANTLR start "ruleDOUBLE"
    // InternalMazeDsl.g:412:1: ruleDOUBLE : ( ( rule__DOUBLE__Group__0 ) ) ;
    public final void ruleDOUBLE() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:416:2: ( ( ( rule__DOUBLE__Group__0 ) ) )
            // InternalMazeDsl.g:417:2: ( ( rule__DOUBLE__Group__0 ) )
            {
            // InternalMazeDsl.g:417:2: ( ( rule__DOUBLE__Group__0 ) )
            // InternalMazeDsl.g:418:3: ( rule__DOUBLE__Group__0 )
            {
             before(grammarAccess.getDOUBLEAccess().getGroup()); 
            // InternalMazeDsl.g:419:3: ( rule__DOUBLE__Group__0 )
            // InternalMazeDsl.g:419:4: rule__DOUBLE__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDOUBLEAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDOUBLE"


    // $ANTLR start "entryRuleSIGNED_INT"
    // InternalMazeDsl.g:428:1: entryRuleSIGNED_INT : ruleSIGNED_INT EOF ;
    public final void entryRuleSIGNED_INT() throws RecognitionException {
        try {
            // InternalMazeDsl.g:429:1: ( ruleSIGNED_INT EOF )
            // InternalMazeDsl.g:430:1: ruleSIGNED_INT EOF
            {
             before(grammarAccess.getSIGNED_INTRule()); 
            pushFollow(FOLLOW_1);
            ruleSIGNED_INT();

            state._fsp--;

             after(grammarAccess.getSIGNED_INTRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSIGNED_INT"


    // $ANTLR start "ruleSIGNED_INT"
    // InternalMazeDsl.g:437:1: ruleSIGNED_INT : ( ( rule__SIGNED_INT__Group__0 ) ) ;
    public final void ruleSIGNED_INT() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:441:2: ( ( ( rule__SIGNED_INT__Group__0 ) ) )
            // InternalMazeDsl.g:442:2: ( ( rule__SIGNED_INT__Group__0 ) )
            {
            // InternalMazeDsl.g:442:2: ( ( rule__SIGNED_INT__Group__0 ) )
            // InternalMazeDsl.g:443:3: ( rule__SIGNED_INT__Group__0 )
            {
             before(grammarAccess.getSIGNED_INTAccess().getGroup()); 
            // InternalMazeDsl.g:444:3: ( rule__SIGNED_INT__Group__0 )
            // InternalMazeDsl.g:444:4: rule__SIGNED_INT__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__SIGNED_INT__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getSIGNED_INTAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSIGNED_INT"


    // $ANTLR start "entryRuleBOOLEAN"
    // InternalMazeDsl.g:453:1: entryRuleBOOLEAN : ruleBOOLEAN EOF ;
    public final void entryRuleBOOLEAN() throws RecognitionException {
        try {
            // InternalMazeDsl.g:454:1: ( ruleBOOLEAN EOF )
            // InternalMazeDsl.g:455:1: ruleBOOLEAN EOF
            {
             before(grammarAccess.getBOOLEANRule()); 
            pushFollow(FOLLOW_1);
            ruleBOOLEAN();

            state._fsp--;

             after(grammarAccess.getBOOLEANRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBOOLEAN"


    // $ANTLR start "ruleBOOLEAN"
    // InternalMazeDsl.g:462:1: ruleBOOLEAN : ( ( rule__BOOLEAN__Alternatives ) ) ;
    public final void ruleBOOLEAN() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:466:2: ( ( ( rule__BOOLEAN__Alternatives ) ) )
            // InternalMazeDsl.g:467:2: ( ( rule__BOOLEAN__Alternatives ) )
            {
            // InternalMazeDsl.g:467:2: ( ( rule__BOOLEAN__Alternatives ) )
            // InternalMazeDsl.g:468:3: ( rule__BOOLEAN__Alternatives )
            {
             before(grammarAccess.getBOOLEANAccess().getAlternatives()); 
            // InternalMazeDsl.g:469:3: ( rule__BOOLEAN__Alternatives )
            // InternalMazeDsl.g:469:4: rule__BOOLEAN__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__BOOLEAN__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getBOOLEANAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBOOLEAN"


    // $ANTLR start "ruleDifficultyLevel"
    // InternalMazeDsl.g:478:1: ruleDifficultyLevel : ( ( rule__DifficultyLevel__Alternatives ) ) ;
    public final void ruleDifficultyLevel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:482:1: ( ( ( rule__DifficultyLevel__Alternatives ) ) )
            // InternalMazeDsl.g:483:2: ( ( rule__DifficultyLevel__Alternatives ) )
            {
            // InternalMazeDsl.g:483:2: ( ( rule__DifficultyLevel__Alternatives ) )
            // InternalMazeDsl.g:484:3: ( rule__DifficultyLevel__Alternatives )
            {
             before(grammarAccess.getDifficultyLevelAccess().getAlternatives()); 
            // InternalMazeDsl.g:485:3: ( rule__DifficultyLevel__Alternatives )
            // InternalMazeDsl.g:485:4: rule__DifficultyLevel__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyLevel__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyLevelAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDifficultyLevel"


    // $ANTLR start "ruleEnemyType"
    // InternalMazeDsl.g:494:1: ruleEnemyType : ( ( rule__EnemyType__Alternatives ) ) ;
    public final void ruleEnemyType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:498:1: ( ( ( rule__EnemyType__Alternatives ) ) )
            // InternalMazeDsl.g:499:2: ( ( rule__EnemyType__Alternatives ) )
            {
            // InternalMazeDsl.g:499:2: ( ( rule__EnemyType__Alternatives ) )
            // InternalMazeDsl.g:500:3: ( rule__EnemyType__Alternatives )
            {
             before(grammarAccess.getEnemyTypeAccess().getAlternatives()); 
            // InternalMazeDsl.g:501:3: ( rule__EnemyType__Alternatives )
            // InternalMazeDsl.g:501:4: rule__EnemyType__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__EnemyType__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getEnemyTypeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEnemyType"


    // $ANTLR start "ruleCharacterTypeEnum"
    // InternalMazeDsl.g:510:1: ruleCharacterTypeEnum : ( ( rule__CharacterTypeEnum__Alternatives ) ) ;
    public final void ruleCharacterTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:514:1: ( ( ( rule__CharacterTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:515:2: ( ( rule__CharacterTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:515:2: ( ( rule__CharacterTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:516:3: ( rule__CharacterTypeEnum__Alternatives )
            {
             before(grammarAccess.getCharacterTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:517:3: ( rule__CharacterTypeEnum__Alternatives )
            // InternalMazeDsl.g:517:4: rule__CharacterTypeEnum__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__CharacterTypeEnum__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getCharacterTypeEnumAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCharacterTypeEnum"


    // $ANTLR start "ruleBehaviorTypeEnum"
    // InternalMazeDsl.g:526:1: ruleBehaviorTypeEnum : ( ( rule__BehaviorTypeEnum__Alternatives ) ) ;
    public final void ruleBehaviorTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:530:1: ( ( ( rule__BehaviorTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:531:2: ( ( rule__BehaviorTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:531:2: ( ( rule__BehaviorTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:532:3: ( rule__BehaviorTypeEnum__Alternatives )
            {
             before(grammarAccess.getBehaviorTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:533:3: ( rule__BehaviorTypeEnum__Alternatives )
            // InternalMazeDsl.g:533:4: rule__BehaviorTypeEnum__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__BehaviorTypeEnum__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getBehaviorTypeEnumAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBehaviorTypeEnum"


    // $ANTLR start "ruleProjectileTypeEnum"
    // InternalMazeDsl.g:542:1: ruleProjectileTypeEnum : ( ( rule__ProjectileTypeEnum__Alternatives ) ) ;
    public final void ruleProjectileTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:546:1: ( ( ( rule__ProjectileTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:547:2: ( ( rule__ProjectileTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:547:2: ( ( rule__ProjectileTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:548:3: ( rule__ProjectileTypeEnum__Alternatives )
            {
             before(grammarAccess.getProjectileTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:549:3: ( rule__ProjectileTypeEnum__Alternatives )
            // InternalMazeDsl.g:549:4: rule__ProjectileTypeEnum__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__ProjectileTypeEnum__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getProjectileTypeEnumAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProjectileTypeEnum"


    // $ANTLR start "ruleLootItemTypeEnum"
    // InternalMazeDsl.g:558:1: ruleLootItemTypeEnum : ( ( rule__LootItemTypeEnum__Alternatives ) ) ;
    public final void ruleLootItemTypeEnum() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:562:1: ( ( ( rule__LootItemTypeEnum__Alternatives ) ) )
            // InternalMazeDsl.g:563:2: ( ( rule__LootItemTypeEnum__Alternatives ) )
            {
            // InternalMazeDsl.g:563:2: ( ( rule__LootItemTypeEnum__Alternatives ) )
            // InternalMazeDsl.g:564:3: ( rule__LootItemTypeEnum__Alternatives )
            {
             before(grammarAccess.getLootItemTypeEnumAccess().getAlternatives()); 
            // InternalMazeDsl.g:565:3: ( rule__LootItemTypeEnum__Alternatives )
            // InternalMazeDsl.g:565:4: rule__LootItemTypeEnum__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__LootItemTypeEnum__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getLootItemTypeEnumAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLootItemTypeEnum"


    // $ANTLR start "rule__CharacterSpecifics__Alternatives"
    // InternalMazeDsl.g:573:1: rule__CharacterSpecifics__Alternatives : ( ( ruleZombieSpecifics ) | ( ruleGhostSpecifics ) | ( ruleRangedSpecifics ) );
    public final void rule__CharacterSpecifics__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:577:1: ( ( ruleZombieSpecifics ) | ( ruleGhostSpecifics ) | ( ruleRangedSpecifics ) )
            int alt1=3;
            switch ( input.LA(1) ) {
            case 51:
                {
                alt1=1;
                }
                break;
            case 55:
                {
                alt1=2;
                }
                break;
            case 58:
                {
                alt1=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // InternalMazeDsl.g:578:2: ( ruleZombieSpecifics )
                    {
                    // InternalMazeDsl.g:578:2: ( ruleZombieSpecifics )
                    // InternalMazeDsl.g:579:3: ruleZombieSpecifics
                    {
                     before(grammarAccess.getCharacterSpecificsAccess().getZombieSpecificsParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleZombieSpecifics();

                    state._fsp--;

                     after(grammarAccess.getCharacterSpecificsAccess().getZombieSpecificsParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:584:2: ( ruleGhostSpecifics )
                    {
                    // InternalMazeDsl.g:584:2: ( ruleGhostSpecifics )
                    // InternalMazeDsl.g:585:3: ruleGhostSpecifics
                    {
                     before(grammarAccess.getCharacterSpecificsAccess().getGhostSpecificsParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleGhostSpecifics();

                    state._fsp--;

                     after(grammarAccess.getCharacterSpecificsAccess().getGhostSpecificsParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:590:2: ( ruleRangedSpecifics )
                    {
                    // InternalMazeDsl.g:590:2: ( ruleRangedSpecifics )
                    // InternalMazeDsl.g:591:3: ruleRangedSpecifics
                    {
                     before(grammarAccess.getCharacterSpecificsAccess().getRangedSpecificsParserRuleCall_2()); 
                    pushFollow(FOLLOW_2);
                    ruleRangedSpecifics();

                    state._fsp--;

                     after(grammarAccess.getCharacterSpecificsAccess().getRangedSpecificsParserRuleCall_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterSpecifics__Alternatives"


    // $ANTLR start "rule__BOOLEAN__Alternatives"
    // InternalMazeDsl.g:600:1: rule__BOOLEAN__Alternatives : ( ( 'true' ) | ( 'false' ) );
    public final void rule__BOOLEAN__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:604:1: ( ( 'true' ) | ( 'false' ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==11) ) {
                alt2=1;
            }
            else if ( (LA2_0==12) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalMazeDsl.g:605:2: ( 'true' )
                    {
                    // InternalMazeDsl.g:605:2: ( 'true' )
                    // InternalMazeDsl.g:606:3: 'true'
                    {
                     before(grammarAccess.getBOOLEANAccess().getTrueKeyword_0()); 
                    match(input,11,FOLLOW_2); 
                     after(grammarAccess.getBOOLEANAccess().getTrueKeyword_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:611:2: ( 'false' )
                    {
                    // InternalMazeDsl.g:611:2: ( 'false' )
                    // InternalMazeDsl.g:612:3: 'false'
                    {
                     before(grammarAccess.getBOOLEANAccess().getFalseKeyword_1()); 
                    match(input,12,FOLLOW_2); 
                     after(grammarAccess.getBOOLEANAccess().getFalseKeyword_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BOOLEAN__Alternatives"


    // $ANTLR start "rule__DifficultyLevel__Alternatives"
    // InternalMazeDsl.g:621:1: rule__DifficultyLevel__Alternatives : ( ( ( 'easy' ) ) | ( ( 'normal' ) ) | ( ( 'hard' ) ) );
    public final void rule__DifficultyLevel__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:625:1: ( ( ( 'easy' ) ) | ( ( 'normal' ) ) | ( ( 'hard' ) ) )
            int alt3=3;
            switch ( input.LA(1) ) {
            case 13:
                {
                alt3=1;
                }
                break;
            case 14:
                {
                alt3=2;
                }
                break;
            case 15:
                {
                alt3=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // InternalMazeDsl.g:626:2: ( ( 'easy' ) )
                    {
                    // InternalMazeDsl.g:626:2: ( ( 'easy' ) )
                    // InternalMazeDsl.g:627:3: ( 'easy' )
                    {
                     before(grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:628:3: ( 'easy' )
                    // InternalMazeDsl.g:628:4: 'easy'
                    {
                    match(input,13,FOLLOW_2); 

                    }

                     after(grammarAccess.getDifficultyLevelAccess().getEASYEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:632:2: ( ( 'normal' ) )
                    {
                    // InternalMazeDsl.g:632:2: ( ( 'normal' ) )
                    // InternalMazeDsl.g:633:3: ( 'normal' )
                    {
                     before(grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:634:3: ( 'normal' )
                    // InternalMazeDsl.g:634:4: 'normal'
                    {
                    match(input,14,FOLLOW_2); 

                    }

                     after(grammarAccess.getDifficultyLevelAccess().getNORMALEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:638:2: ( ( 'hard' ) )
                    {
                    // InternalMazeDsl.g:638:2: ( ( 'hard' ) )
                    // InternalMazeDsl.g:639:3: ( 'hard' )
                    {
                     before(grammarAccess.getDifficultyLevelAccess().getHARDEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:640:3: ( 'hard' )
                    // InternalMazeDsl.g:640:4: 'hard'
                    {
                    match(input,15,FOLLOW_2); 

                    }

                     after(grammarAccess.getDifficultyLevelAccess().getHARDEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyLevel__Alternatives"


    // $ANTLR start "rule__EnemyType__Alternatives"
    // InternalMazeDsl.g:648:1: rule__EnemyType__Alternatives : ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) );
    public final void rule__EnemyType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:652:1: ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) )
            int alt4=3;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt4=1;
                }
                break;
            case 17:
                {
                alt4=2;
                }
                break;
            case 18:
                {
                alt4=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalMazeDsl.g:653:2: ( ( 'zombie' ) )
                    {
                    // InternalMazeDsl.g:653:2: ( ( 'zombie' ) )
                    // InternalMazeDsl.g:654:3: ( 'zombie' )
                    {
                     before(grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:655:3: ( 'zombie' )
                    // InternalMazeDsl.g:655:4: 'zombie'
                    {
                    match(input,16,FOLLOW_2); 

                    }

                     after(grammarAccess.getEnemyTypeAccess().getZOMBIEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:659:2: ( ( 'ghost' ) )
                    {
                    // InternalMazeDsl.g:659:2: ( ( 'ghost' ) )
                    // InternalMazeDsl.g:660:3: ( 'ghost' )
                    {
                     before(grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:661:3: ( 'ghost' )
                    // InternalMazeDsl.g:661:4: 'ghost'
                    {
                    match(input,17,FOLLOW_2); 

                    }

                     after(grammarAccess.getEnemyTypeAccess().getGHOSTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:665:2: ( ( 'pumpkinbomber' ) )
                    {
                    // InternalMazeDsl.g:665:2: ( ( 'pumpkinbomber' ) )
                    // InternalMazeDsl.g:666:3: ( 'pumpkinbomber' )
                    {
                     before(grammarAccess.getEnemyTypeAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:667:3: ( 'pumpkinbomber' )
                    // InternalMazeDsl.g:667:4: 'pumpkinbomber'
                    {
                    match(input,18,FOLLOW_2); 

                    }

                     after(grammarAccess.getEnemyTypeAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyType__Alternatives"


    // $ANTLR start "rule__CharacterTypeEnum__Alternatives"
    // InternalMazeDsl.g:675:1: rule__CharacterTypeEnum__Alternatives : ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) );
    public final void rule__CharacterTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:679:1: ( ( ( 'zombie' ) ) | ( ( 'ghost' ) ) | ( ( 'pumpkinbomber' ) ) )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt5=1;
                }
                break;
            case 17:
                {
                alt5=2;
                }
                break;
            case 18:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // InternalMazeDsl.g:680:2: ( ( 'zombie' ) )
                    {
                    // InternalMazeDsl.g:680:2: ( ( 'zombie' ) )
                    // InternalMazeDsl.g:681:3: ( 'zombie' )
                    {
                     before(grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:682:3: ( 'zombie' )
                    // InternalMazeDsl.g:682:4: 'zombie'
                    {
                    match(input,16,FOLLOW_2); 

                    }

                     after(grammarAccess.getCharacterTypeEnumAccess().getZOMBIEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:686:2: ( ( 'ghost' ) )
                    {
                    // InternalMazeDsl.g:686:2: ( ( 'ghost' ) )
                    // InternalMazeDsl.g:687:3: ( 'ghost' )
                    {
                     before(grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:688:3: ( 'ghost' )
                    // InternalMazeDsl.g:688:4: 'ghost'
                    {
                    match(input,17,FOLLOW_2); 

                    }

                     after(grammarAccess.getCharacterTypeEnumAccess().getGHOSTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:692:2: ( ( 'pumpkinbomber' ) )
                    {
                    // InternalMazeDsl.g:692:2: ( ( 'pumpkinbomber' ) )
                    // InternalMazeDsl.g:693:3: ( 'pumpkinbomber' )
                    {
                     before(grammarAccess.getCharacterTypeEnumAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:694:3: ( 'pumpkinbomber' )
                    // InternalMazeDsl.g:694:4: 'pumpkinbomber'
                    {
                    match(input,18,FOLLOW_2); 

                    }

                     after(grammarAccess.getCharacterTypeEnumAccess().getPUMPKINBOMBEREnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__CharacterTypeEnum__Alternatives"


    // $ANTLR start "rule__BehaviorTypeEnum__Alternatives"
    // InternalMazeDsl.g:702:1: rule__BehaviorTypeEnum__Alternatives : ( ( ( 'passive' ) ) | ( ( 'wander' ) ) | ( ( 'aggressive' ) ) | ( ( 'patrol' ) ) );
    public final void rule__BehaviorTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:706:1: ( ( ( 'passive' ) ) | ( ( 'wander' ) ) | ( ( 'aggressive' ) ) | ( ( 'patrol' ) ) )
            int alt6=4;
            switch ( input.LA(1) ) {
            case 19:
                {
                alt6=1;
                }
                break;
            case 20:
                {
                alt6=2;
                }
                break;
            case 21:
                {
                alt6=3;
                }
                break;
            case 22:
                {
                alt6=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalMazeDsl.g:707:2: ( ( 'passive' ) )
                    {
                    // InternalMazeDsl.g:707:2: ( ( 'passive' ) )
                    // InternalMazeDsl.g:708:3: ( 'passive' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:709:3: ( 'passive' )
                    // InternalMazeDsl.g:709:4: 'passive'
                    {
                    match(input,19,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getPASSIVEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:713:2: ( ( 'wander' ) )
                    {
                    // InternalMazeDsl.g:713:2: ( ( 'wander' ) )
                    // InternalMazeDsl.g:714:3: ( 'wander' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:715:3: ( 'wander' )
                    // InternalMazeDsl.g:715:4: 'wander'
                    {
                    match(input,20,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getWANDEREnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:719:2: ( ( 'aggressive' ) )
                    {
                    // InternalMazeDsl.g:719:2: ( ( 'aggressive' ) )
                    // InternalMazeDsl.g:720:3: ( 'aggressive' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:721:3: ( 'aggressive' )
                    // InternalMazeDsl.g:721:4: 'aggressive'
                    {
                    match(input,21,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getAGGRESSIVEEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:725:2: ( ( 'patrol' ) )
                    {
                    // InternalMazeDsl.g:725:2: ( ( 'patrol' ) )
                    // InternalMazeDsl.g:726:3: ( 'patrol' )
                    {
                     before(grammarAccess.getBehaviorTypeEnumAccess().getPATROLEnumLiteralDeclaration_3()); 
                    // InternalMazeDsl.g:727:3: ( 'patrol' )
                    // InternalMazeDsl.g:727:4: 'patrol'
                    {
                    match(input,22,FOLLOW_2); 

                    }

                     after(grammarAccess.getBehaviorTypeEnumAccess().getPATROLEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BehaviorTypeEnum__Alternatives"


    // $ANTLR start "rule__ProjectileTypeEnum__Alternatives"
    // InternalMazeDsl.g:735:1: rule__ProjectileTypeEnum__Alternatives : ( ( ( 'straight' ) ) | ( ( 'lob' ) ) | ( ( 'beam' ) ) );
    public final void rule__ProjectileTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:739:1: ( ( ( 'straight' ) ) | ( ( 'lob' ) ) | ( ( 'beam' ) ) )
            int alt7=3;
            switch ( input.LA(1) ) {
            case 23:
                {
                alt7=1;
                }
                break;
            case 24:
                {
                alt7=2;
                }
                break;
            case 25:
                {
                alt7=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // InternalMazeDsl.g:740:2: ( ( 'straight' ) )
                    {
                    // InternalMazeDsl.g:740:2: ( ( 'straight' ) )
                    // InternalMazeDsl.g:741:3: ( 'straight' )
                    {
                     before(grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:742:3: ( 'straight' )
                    // InternalMazeDsl.g:742:4: 'straight'
                    {
                    match(input,23,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectileTypeEnumAccess().getSTRAIGHTEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:746:2: ( ( 'lob' ) )
                    {
                    // InternalMazeDsl.g:746:2: ( ( 'lob' ) )
                    // InternalMazeDsl.g:747:3: ( 'lob' )
                    {
                     before(grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:748:3: ( 'lob' )
                    // InternalMazeDsl.g:748:4: 'lob'
                    {
                    match(input,24,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectileTypeEnumAccess().getLOBEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:752:2: ( ( 'beam' ) )
                    {
                    // InternalMazeDsl.g:752:2: ( ( 'beam' ) )
                    // InternalMazeDsl.g:753:3: ( 'beam' )
                    {
                     before(grammarAccess.getProjectileTypeEnumAccess().getBEAMEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:754:3: ( 'beam' )
                    // InternalMazeDsl.g:754:4: 'beam'
                    {
                    match(input,25,FOLLOW_2); 

                    }

                     after(grammarAccess.getProjectileTypeEnumAccess().getBEAMEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProjectileTypeEnum__Alternatives"


    // $ANTLR start "rule__LootItemTypeEnum__Alternatives"
    // InternalMazeDsl.g:762:1: rule__LootItemTypeEnum__Alternatives : ( ( ( 'food' ) ) | ( ( 'bomb' ) ) | ( ( 'trap' ) ) | ( ( 'weapon' ) ) );
    public final void rule__LootItemTypeEnum__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:766:1: ( ( ( 'food' ) ) | ( ( 'bomb' ) ) | ( ( 'trap' ) ) | ( ( 'weapon' ) ) )
            int alt8=4;
            switch ( input.LA(1) ) {
            case 26:
                {
                alt8=1;
                }
                break;
            case 27:
                {
                alt8=2;
                }
                break;
            case 28:
                {
                alt8=3;
                }
                break;
            case 29:
                {
                alt8=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // InternalMazeDsl.g:767:2: ( ( 'food' ) )
                    {
                    // InternalMazeDsl.g:767:2: ( ( 'food' ) )
                    // InternalMazeDsl.g:768:3: ( 'food' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0()); 
                    // InternalMazeDsl.g:769:3: ( 'food' )
                    // InternalMazeDsl.g:769:4: 'food'
                    {
                    match(input,26,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getFOODEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalMazeDsl.g:773:2: ( ( 'bomb' ) )
                    {
                    // InternalMazeDsl.g:773:2: ( ( 'bomb' ) )
                    // InternalMazeDsl.g:774:3: ( 'bomb' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1()); 
                    // InternalMazeDsl.g:775:3: ( 'bomb' )
                    // InternalMazeDsl.g:775:4: 'bomb'
                    {
                    match(input,27,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getBOMBEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalMazeDsl.g:779:2: ( ( 'trap' ) )
                    {
                    // InternalMazeDsl.g:779:2: ( ( 'trap' ) )
                    // InternalMazeDsl.g:780:3: ( 'trap' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2()); 
                    // InternalMazeDsl.g:781:3: ( 'trap' )
                    // InternalMazeDsl.g:781:4: 'trap'
                    {
                    match(input,28,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getTRAPEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalMazeDsl.g:785:2: ( ( 'weapon' ) )
                    {
                    // InternalMazeDsl.g:785:2: ( ( 'weapon' ) )
                    // InternalMazeDsl.g:786:3: ( 'weapon' )
                    {
                     before(grammarAccess.getLootItemTypeEnumAccess().getWEAPONEnumLiteralDeclaration_3()); 
                    // InternalMazeDsl.g:787:3: ( 'weapon' )
                    // InternalMazeDsl.g:787:4: 'weapon'
                    {
                    match(input,29,FOLLOW_2); 

                    }

                     after(grammarAccess.getLootItemTypeEnumAccess().getWEAPONEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemTypeEnum__Alternatives"


    // $ANTLR start "rule__GameConfiguration__Group__0"
    // InternalMazeDsl.g:795:1: rule__GameConfiguration__Group__0 : rule__GameConfiguration__Group__0__Impl rule__GameConfiguration__Group__1 ;
    public final void rule__GameConfiguration__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:799:1: ( rule__GameConfiguration__Group__0__Impl rule__GameConfiguration__Group__1 )
            // InternalMazeDsl.g:800:2: rule__GameConfiguration__Group__0__Impl rule__GameConfiguration__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__GameConfiguration__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__0"


    // $ANTLR start "rule__GameConfiguration__Group__0__Impl"
    // InternalMazeDsl.g:807:1: rule__GameConfiguration__Group__0__Impl : ( 'game' ) ;
    public final void rule__GameConfiguration__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:811:1: ( ( 'game' ) )
            // InternalMazeDsl.g:812:1: ( 'game' )
            {
            // InternalMazeDsl.g:812:1: ( 'game' )
            // InternalMazeDsl.g:813:2: 'game'
            {
             before(grammarAccess.getGameConfigurationAccess().getGameKeyword_0()); 
            match(input,30,FOLLOW_2); 
             after(grammarAccess.getGameConfigurationAccess().getGameKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__0__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__1"
    // InternalMazeDsl.g:822:1: rule__GameConfiguration__Group__1 : rule__GameConfiguration__Group__1__Impl rule__GameConfiguration__Group__2 ;
    public final void rule__GameConfiguration__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:826:1: ( rule__GameConfiguration__Group__1__Impl rule__GameConfiguration__Group__2 )
            // InternalMazeDsl.g:827:2: rule__GameConfiguration__Group__1__Impl rule__GameConfiguration__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__GameConfiguration__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__1"


    // $ANTLR start "rule__GameConfiguration__Group__1__Impl"
    // InternalMazeDsl.g:834:1: rule__GameConfiguration__Group__1__Impl : ( ( rule__GameConfiguration__NameAssignment_1 ) ) ;
    public final void rule__GameConfiguration__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:838:1: ( ( ( rule__GameConfiguration__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:839:1: ( ( rule__GameConfiguration__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:839:1: ( ( rule__GameConfiguration__NameAssignment_1 ) )
            // InternalMazeDsl.g:840:2: ( rule__GameConfiguration__NameAssignment_1 )
            {
             before(grammarAccess.getGameConfigurationAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:841:2: ( rule__GameConfiguration__NameAssignment_1 )
            // InternalMazeDsl.g:841:3: rule__GameConfiguration__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__GameConfiguration__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getGameConfigurationAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__1__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__2"
    // InternalMazeDsl.g:849:1: rule__GameConfiguration__Group__2 : rule__GameConfiguration__Group__2__Impl rule__GameConfiguration__Group__3 ;
    public final void rule__GameConfiguration__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:853:1: ( rule__GameConfiguration__Group__2__Impl rule__GameConfiguration__Group__3 )
            // InternalMazeDsl.g:854:2: rule__GameConfiguration__Group__2__Impl rule__GameConfiguration__Group__3
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__2"


    // $ANTLR start "rule__GameConfiguration__Group__2__Impl"
    // InternalMazeDsl.g:861:1: rule__GameConfiguration__Group__2__Impl : ( '{' ) ;
    public final void rule__GameConfiguration__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:865:1: ( ( '{' ) )
            // InternalMazeDsl.g:866:1: ( '{' )
            {
            // InternalMazeDsl.g:866:1: ( '{' )
            // InternalMazeDsl.g:867:2: '{'
            {
             before(grammarAccess.getGameConfigurationAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getGameConfigurationAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__2__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__3"
    // InternalMazeDsl.g:876:1: rule__GameConfiguration__Group__3 : rule__GameConfiguration__Group__3__Impl rule__GameConfiguration__Group__4 ;
    public final void rule__GameConfiguration__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:880:1: ( rule__GameConfiguration__Group__3__Impl rule__GameConfiguration__Group__4 )
            // InternalMazeDsl.g:881:2: rule__GameConfiguration__Group__3__Impl rule__GameConfiguration__Group__4
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__3"


    // $ANTLR start "rule__GameConfiguration__Group__3__Impl"
    // InternalMazeDsl.g:888:1: rule__GameConfiguration__Group__3__Impl : ( ( rule__GameConfiguration__ImportsAssignment_3 )* ) ;
    public final void rule__GameConfiguration__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:892:1: ( ( ( rule__GameConfiguration__ImportsAssignment_3 )* ) )
            // InternalMazeDsl.g:893:1: ( ( rule__GameConfiguration__ImportsAssignment_3 )* )
            {
            // InternalMazeDsl.g:893:1: ( ( rule__GameConfiguration__ImportsAssignment_3 )* )
            // InternalMazeDsl.g:894:2: ( rule__GameConfiguration__ImportsAssignment_3 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getImportsAssignment_3()); 
            // InternalMazeDsl.g:895:2: ( rule__GameConfiguration__ImportsAssignment_3 )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==33) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalMazeDsl.g:895:3: rule__GameConfiguration__ImportsAssignment_3
            	    {
            	    pushFollow(FOLLOW_6);
            	    rule__GameConfiguration__ImportsAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

             after(grammarAccess.getGameConfigurationAccess().getImportsAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__3__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__4"
    // InternalMazeDsl.g:903:1: rule__GameConfiguration__Group__4 : rule__GameConfiguration__Group__4__Impl rule__GameConfiguration__Group__5 ;
    public final void rule__GameConfiguration__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:907:1: ( rule__GameConfiguration__Group__4__Impl rule__GameConfiguration__Group__5 )
            // InternalMazeDsl.g:908:2: rule__GameConfiguration__Group__4__Impl rule__GameConfiguration__Group__5
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__4"


    // $ANTLR start "rule__GameConfiguration__Group__4__Impl"
    // InternalMazeDsl.g:915:1: rule__GameConfiguration__Group__4__Impl : ( ( rule__GameConfiguration__DifficultyAssignment_4 )? ) ;
    public final void rule__GameConfiguration__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:919:1: ( ( ( rule__GameConfiguration__DifficultyAssignment_4 )? ) )
            // InternalMazeDsl.g:920:1: ( ( rule__GameConfiguration__DifficultyAssignment_4 )? )
            {
            // InternalMazeDsl.g:920:1: ( ( rule__GameConfiguration__DifficultyAssignment_4 )? )
            // InternalMazeDsl.g:921:2: ( rule__GameConfiguration__DifficultyAssignment_4 )?
            {
             before(grammarAccess.getGameConfigurationAccess().getDifficultyAssignment_4()); 
            // InternalMazeDsl.g:922:2: ( rule__GameConfiguration__DifficultyAssignment_4 )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==34) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalMazeDsl.g:922:3: rule__GameConfiguration__DifficultyAssignment_4
                    {
                    pushFollow(FOLLOW_2);
                    rule__GameConfiguration__DifficultyAssignment_4();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGameConfigurationAccess().getDifficultyAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__4__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__5"
    // InternalMazeDsl.g:930:1: rule__GameConfiguration__Group__5 : rule__GameConfiguration__Group__5__Impl rule__GameConfiguration__Group__6 ;
    public final void rule__GameConfiguration__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:934:1: ( rule__GameConfiguration__Group__5__Impl rule__GameConfiguration__Group__6 )
            // InternalMazeDsl.g:935:2: rule__GameConfiguration__Group__5__Impl rule__GameConfiguration__Group__6
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__5"


    // $ANTLR start "rule__GameConfiguration__Group__5__Impl"
    // InternalMazeDsl.g:942:1: rule__GameConfiguration__Group__5__Impl : ( ( rule__GameConfiguration__OpponentsAssignment_5 )* ) ;
    public final void rule__GameConfiguration__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:946:1: ( ( ( rule__GameConfiguration__OpponentsAssignment_5 )* ) )
            // InternalMazeDsl.g:947:1: ( ( rule__GameConfiguration__OpponentsAssignment_5 )* )
            {
            // InternalMazeDsl.g:947:1: ( ( rule__GameConfiguration__OpponentsAssignment_5 )* )
            // InternalMazeDsl.g:948:2: ( rule__GameConfiguration__OpponentsAssignment_5 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getOpponentsAssignment_5()); 
            // InternalMazeDsl.g:949:2: ( rule__GameConfiguration__OpponentsAssignment_5 )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==42) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalMazeDsl.g:949:3: rule__GameConfiguration__OpponentsAssignment_5
            	    {
            	    pushFollow(FOLLOW_7);
            	    rule__GameConfiguration__OpponentsAssignment_5();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

             after(grammarAccess.getGameConfigurationAccess().getOpponentsAssignment_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__5__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__6"
    // InternalMazeDsl.g:957:1: rule__GameConfiguration__Group__6 : rule__GameConfiguration__Group__6__Impl rule__GameConfiguration__Group__7 ;
    public final void rule__GameConfiguration__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:961:1: ( rule__GameConfiguration__Group__6__Impl rule__GameConfiguration__Group__7 )
            // InternalMazeDsl.g:962:2: rule__GameConfiguration__Group__6__Impl rule__GameConfiguration__Group__7
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__6"


    // $ANTLR start "rule__GameConfiguration__Group__6__Impl"
    // InternalMazeDsl.g:969:1: rule__GameConfiguration__Group__6__Impl : ( ( rule__GameConfiguration__PatrolsAssignment_6 )* ) ;
    public final void rule__GameConfiguration__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:973:1: ( ( ( rule__GameConfiguration__PatrolsAssignment_6 )* ) )
            // InternalMazeDsl.g:974:1: ( ( rule__GameConfiguration__PatrolsAssignment_6 )* )
            {
            // InternalMazeDsl.g:974:1: ( ( rule__GameConfiguration__PatrolsAssignment_6 )* )
            // InternalMazeDsl.g:975:2: ( rule__GameConfiguration__PatrolsAssignment_6 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getPatrolsAssignment_6()); 
            // InternalMazeDsl.g:976:2: ( rule__GameConfiguration__PatrolsAssignment_6 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==22) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalMazeDsl.g:976:3: rule__GameConfiguration__PatrolsAssignment_6
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__GameConfiguration__PatrolsAssignment_6();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

             after(grammarAccess.getGameConfigurationAccess().getPatrolsAssignment_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__6__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__7"
    // InternalMazeDsl.g:984:1: rule__GameConfiguration__Group__7 : rule__GameConfiguration__Group__7__Impl rule__GameConfiguration__Group__8 ;
    public final void rule__GameConfiguration__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:988:1: ( rule__GameConfiguration__Group__7__Impl rule__GameConfiguration__Group__8 )
            // InternalMazeDsl.g:989:2: rule__GameConfiguration__Group__7__Impl rule__GameConfiguration__Group__8
            {
            pushFollow(FOLLOW_5);
            rule__GameConfiguration__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__7"


    // $ANTLR start "rule__GameConfiguration__Group__7__Impl"
    // InternalMazeDsl.g:996:1: rule__GameConfiguration__Group__7__Impl : ( ( rule__GameConfiguration__LootTablesAssignment_7 )* ) ;
    public final void rule__GameConfiguration__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1000:1: ( ( ( rule__GameConfiguration__LootTablesAssignment_7 )* ) )
            // InternalMazeDsl.g:1001:1: ( ( rule__GameConfiguration__LootTablesAssignment_7 )* )
            {
            // InternalMazeDsl.g:1001:1: ( ( rule__GameConfiguration__LootTablesAssignment_7 )* )
            // InternalMazeDsl.g:1002:2: ( rule__GameConfiguration__LootTablesAssignment_7 )*
            {
             before(grammarAccess.getGameConfigurationAccess().getLootTablesAssignment_7()); 
            // InternalMazeDsl.g:1003:2: ( rule__GameConfiguration__LootTablesAssignment_7 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==77) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalMazeDsl.g:1003:3: rule__GameConfiguration__LootTablesAssignment_7
            	    {
            	    pushFollow(FOLLOW_9);
            	    rule__GameConfiguration__LootTablesAssignment_7();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

             after(grammarAccess.getGameConfigurationAccess().getLootTablesAssignment_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__7__Impl"


    // $ANTLR start "rule__GameConfiguration__Group__8"
    // InternalMazeDsl.g:1011:1: rule__GameConfiguration__Group__8 : rule__GameConfiguration__Group__8__Impl ;
    public final void rule__GameConfiguration__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1015:1: ( rule__GameConfiguration__Group__8__Impl )
            // InternalMazeDsl.g:1016:2: rule__GameConfiguration__Group__8__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GameConfiguration__Group__8__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__8"


    // $ANTLR start "rule__GameConfiguration__Group__8__Impl"
    // InternalMazeDsl.g:1022:1: rule__GameConfiguration__Group__8__Impl : ( '}' ) ;
    public final void rule__GameConfiguration__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1026:1: ( ( '}' ) )
            // InternalMazeDsl.g:1027:1: ( '}' )
            {
            // InternalMazeDsl.g:1027:1: ( '}' )
            // InternalMazeDsl.g:1028:2: '}'
            {
             before(grammarAccess.getGameConfigurationAccess().getRightCurlyBracketKeyword_8()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getGameConfigurationAccess().getRightCurlyBracketKeyword_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__Group__8__Impl"


    // $ANTLR start "rule__Import__Group__0"
    // InternalMazeDsl.g:1038:1: rule__Import__Group__0 : rule__Import__Group__0__Impl rule__Import__Group__1 ;
    public final void rule__Import__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1042:1: ( rule__Import__Group__0__Impl rule__Import__Group__1 )
            // InternalMazeDsl.g:1043:2: rule__Import__Group__0__Impl rule__Import__Group__1
            {
            pushFollow(FOLLOW_10);
            rule__Import__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Import__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__0"


    // $ANTLR start "rule__Import__Group__0__Impl"
    // InternalMazeDsl.g:1050:1: rule__Import__Group__0__Impl : ( 'import' ) ;
    public final void rule__Import__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1054:1: ( ( 'import' ) )
            // InternalMazeDsl.g:1055:1: ( 'import' )
            {
            // InternalMazeDsl.g:1055:1: ( 'import' )
            // InternalMazeDsl.g:1056:2: 'import'
            {
             before(grammarAccess.getImportAccess().getImportKeyword_0()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getImportAccess().getImportKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__0__Impl"


    // $ANTLR start "rule__Import__Group__1"
    // InternalMazeDsl.g:1065:1: rule__Import__Group__1 : rule__Import__Group__1__Impl ;
    public final void rule__Import__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1069:1: ( rule__Import__Group__1__Impl )
            // InternalMazeDsl.g:1070:2: rule__Import__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Import__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__1"


    // $ANTLR start "rule__Import__Group__1__Impl"
    // InternalMazeDsl.g:1076:1: rule__Import__Group__1__Impl : ( ( rule__Import__ImportURIAssignment_1 ) ) ;
    public final void rule__Import__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1080:1: ( ( ( rule__Import__ImportURIAssignment_1 ) ) )
            // InternalMazeDsl.g:1081:1: ( ( rule__Import__ImportURIAssignment_1 ) )
            {
            // InternalMazeDsl.g:1081:1: ( ( rule__Import__ImportURIAssignment_1 ) )
            // InternalMazeDsl.g:1082:2: ( rule__Import__ImportURIAssignment_1 )
            {
             before(grammarAccess.getImportAccess().getImportURIAssignment_1()); 
            // InternalMazeDsl.g:1083:2: ( rule__Import__ImportURIAssignment_1 )
            // InternalMazeDsl.g:1083:3: rule__Import__ImportURIAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Import__ImportURIAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getImportAccess().getImportURIAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__1__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__0"
    // InternalMazeDsl.g:1092:1: rule__DifficultyConfig__Group__0 : rule__DifficultyConfig__Group__0__Impl rule__DifficultyConfig__Group__1 ;
    public final void rule__DifficultyConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1096:1: ( rule__DifficultyConfig__Group__0__Impl rule__DifficultyConfig__Group__1 )
            // InternalMazeDsl.g:1097:2: rule__DifficultyConfig__Group__0__Impl rule__DifficultyConfig__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__DifficultyConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__0"


    // $ANTLR start "rule__DifficultyConfig__Group__0__Impl"
    // InternalMazeDsl.g:1104:1: rule__DifficultyConfig__Group__0__Impl : ( 'difficulty' ) ;
    public final void rule__DifficultyConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1108:1: ( ( 'difficulty' ) )
            // InternalMazeDsl.g:1109:1: ( 'difficulty' )
            {
            // InternalMazeDsl.g:1109:1: ( 'difficulty' )
            // InternalMazeDsl.g:1110:2: 'difficulty'
            {
             before(grammarAccess.getDifficultyConfigAccess().getDifficultyKeyword_0()); 
            match(input,34,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getDifficultyKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__0__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__1"
    // InternalMazeDsl.g:1119:1: rule__DifficultyConfig__Group__1 : rule__DifficultyConfig__Group__1__Impl rule__DifficultyConfig__Group__2 ;
    public final void rule__DifficultyConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1123:1: ( rule__DifficultyConfig__Group__1__Impl rule__DifficultyConfig__Group__2 )
            // InternalMazeDsl.g:1124:2: rule__DifficultyConfig__Group__1__Impl rule__DifficultyConfig__Group__2
            {
            pushFollow(FOLLOW_11);
            rule__DifficultyConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__1"


    // $ANTLR start "rule__DifficultyConfig__Group__1__Impl"
    // InternalMazeDsl.g:1131:1: rule__DifficultyConfig__Group__1__Impl : ( '{' ) ;
    public final void rule__DifficultyConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1135:1: ( ( '{' ) )
            // InternalMazeDsl.g:1136:1: ( '{' )
            {
            // InternalMazeDsl.g:1136:1: ( '{' )
            // InternalMazeDsl.g:1137:2: '{'
            {
             before(grammarAccess.getDifficultyConfigAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getLeftCurlyBracketKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__1__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__2"
    // InternalMazeDsl.g:1146:1: rule__DifficultyConfig__Group__2 : rule__DifficultyConfig__Group__2__Impl rule__DifficultyConfig__Group__3 ;
    public final void rule__DifficultyConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1150:1: ( rule__DifficultyConfig__Group__2__Impl rule__DifficultyConfig__Group__3 )
            // InternalMazeDsl.g:1151:2: rule__DifficultyConfig__Group__2__Impl rule__DifficultyConfig__Group__3
            {
            pushFollow(FOLLOW_12);
            rule__DifficultyConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__2"


    // $ANTLR start "rule__DifficultyConfig__Group__2__Impl"
    // InternalMazeDsl.g:1158:1: rule__DifficultyConfig__Group__2__Impl : ( 'level' ) ;
    public final void rule__DifficultyConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1162:1: ( ( 'level' ) )
            // InternalMazeDsl.g:1163:1: ( 'level' )
            {
            // InternalMazeDsl.g:1163:1: ( 'level' )
            // InternalMazeDsl.g:1164:2: 'level'
            {
             before(grammarAccess.getDifficultyConfigAccess().getLevelKeyword_2()); 
            match(input,35,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getLevelKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__2__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__3"
    // InternalMazeDsl.g:1173:1: rule__DifficultyConfig__Group__3 : rule__DifficultyConfig__Group__3__Impl rule__DifficultyConfig__Group__4 ;
    public final void rule__DifficultyConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1177:1: ( rule__DifficultyConfig__Group__3__Impl rule__DifficultyConfig__Group__4 )
            // InternalMazeDsl.g:1178:2: rule__DifficultyConfig__Group__3__Impl rule__DifficultyConfig__Group__4
            {
            pushFollow(FOLLOW_13);
            rule__DifficultyConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__3"


    // $ANTLR start "rule__DifficultyConfig__Group__3__Impl"
    // InternalMazeDsl.g:1185:1: rule__DifficultyConfig__Group__3__Impl : ( ( rule__DifficultyConfig__LevelAssignment_3 ) ) ;
    public final void rule__DifficultyConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1189:1: ( ( ( rule__DifficultyConfig__LevelAssignment_3 ) ) )
            // InternalMazeDsl.g:1190:1: ( ( rule__DifficultyConfig__LevelAssignment_3 ) )
            {
            // InternalMazeDsl.g:1190:1: ( ( rule__DifficultyConfig__LevelAssignment_3 ) )
            // InternalMazeDsl.g:1191:2: ( rule__DifficultyConfig__LevelAssignment_3 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getLevelAssignment_3()); 
            // InternalMazeDsl.g:1192:2: ( rule__DifficultyConfig__LevelAssignment_3 )
            // InternalMazeDsl.g:1192:3: rule__DifficultyConfig__LevelAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__LevelAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyConfigAccess().getLevelAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__3__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__4"
    // InternalMazeDsl.g:1200:1: rule__DifficultyConfig__Group__4 : rule__DifficultyConfig__Group__4__Impl rule__DifficultyConfig__Group__5 ;
    public final void rule__DifficultyConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1204:1: ( rule__DifficultyConfig__Group__4__Impl rule__DifficultyConfig__Group__5 )
            // InternalMazeDsl.g:1205:2: rule__DifficultyConfig__Group__4__Impl rule__DifficultyConfig__Group__5
            {
            pushFollow(FOLLOW_13);
            rule__DifficultyConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__4"


    // $ANTLR start "rule__DifficultyConfig__Group__4__Impl"
    // InternalMazeDsl.g:1212:1: rule__DifficultyConfig__Group__4__Impl : ( ( rule__DifficultyConfig__Group_4__0 )? ) ;
    public final void rule__DifficultyConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1216:1: ( ( ( rule__DifficultyConfig__Group_4__0 )? ) )
            // InternalMazeDsl.g:1217:1: ( ( rule__DifficultyConfig__Group_4__0 )? )
            {
            // InternalMazeDsl.g:1217:1: ( ( rule__DifficultyConfig__Group_4__0 )? )
            // InternalMazeDsl.g:1218:2: ( rule__DifficultyConfig__Group_4__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_4()); 
            // InternalMazeDsl.g:1219:2: ( rule__DifficultyConfig__Group_4__0 )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==36) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalMazeDsl.g:1219:3: rule__DifficultyConfig__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DifficultyConfig__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDifficultyConfigAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__4__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__5"
    // InternalMazeDsl.g:1227:1: rule__DifficultyConfig__Group__5 : rule__DifficultyConfig__Group__5__Impl rule__DifficultyConfig__Group__6 ;
    public final void rule__DifficultyConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1231:1: ( rule__DifficultyConfig__Group__5__Impl rule__DifficultyConfig__Group__6 )
            // InternalMazeDsl.g:1232:2: rule__DifficultyConfig__Group__5__Impl rule__DifficultyConfig__Group__6
            {
            pushFollow(FOLLOW_13);
            rule__DifficultyConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__5"


    // $ANTLR start "rule__DifficultyConfig__Group__5__Impl"
    // InternalMazeDsl.g:1239:1: rule__DifficultyConfig__Group__5__Impl : ( ( rule__DifficultyConfig__Group_5__0 )? ) ;
    public final void rule__DifficultyConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1243:1: ( ( ( rule__DifficultyConfig__Group_5__0 )? ) )
            // InternalMazeDsl.g:1244:1: ( ( rule__DifficultyConfig__Group_5__0 )? )
            {
            // InternalMazeDsl.g:1244:1: ( ( rule__DifficultyConfig__Group_5__0 )? )
            // InternalMazeDsl.g:1245:2: ( rule__DifficultyConfig__Group_5__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_5()); 
            // InternalMazeDsl.g:1246:2: ( rule__DifficultyConfig__Group_5__0 )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==37) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalMazeDsl.g:1246:3: rule__DifficultyConfig__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DifficultyConfig__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDifficultyConfigAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__5__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__6"
    // InternalMazeDsl.g:1254:1: rule__DifficultyConfig__Group__6 : rule__DifficultyConfig__Group__6__Impl rule__DifficultyConfig__Group__7 ;
    public final void rule__DifficultyConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1258:1: ( rule__DifficultyConfig__Group__6__Impl rule__DifficultyConfig__Group__7 )
            // InternalMazeDsl.g:1259:2: rule__DifficultyConfig__Group__6__Impl rule__DifficultyConfig__Group__7
            {
            pushFollow(FOLLOW_13);
            rule__DifficultyConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__6"


    // $ANTLR start "rule__DifficultyConfig__Group__6__Impl"
    // InternalMazeDsl.g:1266:1: rule__DifficultyConfig__Group__6__Impl : ( ( rule__DifficultyConfig__Group_6__0 )? ) ;
    public final void rule__DifficultyConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1270:1: ( ( ( rule__DifficultyConfig__Group_6__0 )? ) )
            // InternalMazeDsl.g:1271:1: ( ( rule__DifficultyConfig__Group_6__0 )? )
            {
            // InternalMazeDsl.g:1271:1: ( ( rule__DifficultyConfig__Group_6__0 )? )
            // InternalMazeDsl.g:1272:2: ( rule__DifficultyConfig__Group_6__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_6()); 
            // InternalMazeDsl.g:1273:2: ( rule__DifficultyConfig__Group_6__0 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==38) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalMazeDsl.g:1273:3: rule__DifficultyConfig__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DifficultyConfig__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDifficultyConfigAccess().getGroup_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__6__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__7"
    // InternalMazeDsl.g:1281:1: rule__DifficultyConfig__Group__7 : rule__DifficultyConfig__Group__7__Impl rule__DifficultyConfig__Group__8 ;
    public final void rule__DifficultyConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1285:1: ( rule__DifficultyConfig__Group__7__Impl rule__DifficultyConfig__Group__8 )
            // InternalMazeDsl.g:1286:2: rule__DifficultyConfig__Group__7__Impl rule__DifficultyConfig__Group__8
            {
            pushFollow(FOLLOW_13);
            rule__DifficultyConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__7"


    // $ANTLR start "rule__DifficultyConfig__Group__7__Impl"
    // InternalMazeDsl.g:1293:1: rule__DifficultyConfig__Group__7__Impl : ( ( rule__DifficultyConfig__Group_7__0 )? ) ;
    public final void rule__DifficultyConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1297:1: ( ( ( rule__DifficultyConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:1298:1: ( ( rule__DifficultyConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:1298:1: ( ( rule__DifficultyConfig__Group_7__0 )? )
            // InternalMazeDsl.g:1299:2: ( rule__DifficultyConfig__Group_7__0 )?
            {
             before(grammarAccess.getDifficultyConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:1300:2: ( rule__DifficultyConfig__Group_7__0 )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==39) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalMazeDsl.g:1300:3: rule__DifficultyConfig__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DifficultyConfig__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDifficultyConfigAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__7__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__8"
    // InternalMazeDsl.g:1308:1: rule__DifficultyConfig__Group__8 : rule__DifficultyConfig__Group__8__Impl rule__DifficultyConfig__Group__9 ;
    public final void rule__DifficultyConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1312:1: ( rule__DifficultyConfig__Group__8__Impl rule__DifficultyConfig__Group__9 )
            // InternalMazeDsl.g:1313:2: rule__DifficultyConfig__Group__8__Impl rule__DifficultyConfig__Group__9
            {
            pushFollow(FOLLOW_13);
            rule__DifficultyConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__8"


    // $ANTLR start "rule__DifficultyConfig__Group__8__Impl"
    // InternalMazeDsl.g:1320:1: rule__DifficultyConfig__Group__8__Impl : ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* ) ;
    public final void rule__DifficultyConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1324:1: ( ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* ) )
            // InternalMazeDsl.g:1325:1: ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* )
            {
            // InternalMazeDsl.g:1325:1: ( ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )* )
            // InternalMazeDsl.g:1326:2: ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )*
            {
             before(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsAssignment_8()); 
            // InternalMazeDsl.g:1327:2: ( rule__DifficultyConfig__EnemyLimitsAssignment_8 )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==40) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalMazeDsl.g:1327:3: rule__DifficultyConfig__EnemyLimitsAssignment_8
            	    {
            	    pushFollow(FOLLOW_14);
            	    rule__DifficultyConfig__EnemyLimitsAssignment_8();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

             after(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsAssignment_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__8__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group__9"
    // InternalMazeDsl.g:1335:1: rule__DifficultyConfig__Group__9 : rule__DifficultyConfig__Group__9__Impl ;
    public final void rule__DifficultyConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1339:1: ( rule__DifficultyConfig__Group__9__Impl )
            // InternalMazeDsl.g:1340:2: rule__DifficultyConfig__Group__9__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group__9__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__9"


    // $ANTLR start "rule__DifficultyConfig__Group__9__Impl"
    // InternalMazeDsl.g:1346:1: rule__DifficultyConfig__Group__9__Impl : ( '}' ) ;
    public final void rule__DifficultyConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1350:1: ( ( '}' ) )
            // InternalMazeDsl.g:1351:1: ( '}' )
            {
            // InternalMazeDsl.g:1351:1: ( '}' )
            // InternalMazeDsl.g:1352:2: '}'
            {
             before(grammarAccess.getDifficultyConfigAccess().getRightCurlyBracketKeyword_9()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getRightCurlyBracketKeyword_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group__9__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_4__0"
    // InternalMazeDsl.g:1362:1: rule__DifficultyConfig__Group_4__0 : rule__DifficultyConfig__Group_4__0__Impl rule__DifficultyConfig__Group_4__1 ;
    public final void rule__DifficultyConfig__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1366:1: ( rule__DifficultyConfig__Group_4__0__Impl rule__DifficultyConfig__Group_4__1 )
            // InternalMazeDsl.g:1367:2: rule__DifficultyConfig__Group_4__0__Impl rule__DifficultyConfig__Group_4__1
            {
            pushFollow(FOLLOW_15);
            rule__DifficultyConfig__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_4__0"


    // $ANTLR start "rule__DifficultyConfig__Group_4__0__Impl"
    // InternalMazeDsl.g:1374:1: rule__DifficultyConfig__Group_4__0__Impl : ( 'instantDeath' ) ;
    public final void rule__DifficultyConfig__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1378:1: ( ( 'instantDeath' ) )
            // InternalMazeDsl.g:1379:1: ( 'instantDeath' )
            {
            // InternalMazeDsl.g:1379:1: ( 'instantDeath' )
            // InternalMazeDsl.g:1380:2: 'instantDeath'
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathKeyword_4_0()); 
            match(input,36,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getInstantDeathKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_4__0__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_4__1"
    // InternalMazeDsl.g:1389:1: rule__DifficultyConfig__Group_4__1 : rule__DifficultyConfig__Group_4__1__Impl ;
    public final void rule__DifficultyConfig__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1393:1: ( rule__DifficultyConfig__Group_4__1__Impl )
            // InternalMazeDsl.g:1394:2: rule__DifficultyConfig__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_4__1"


    // $ANTLR start "rule__DifficultyConfig__Group_4__1__Impl"
    // InternalMazeDsl.g:1400:1: rule__DifficultyConfig__Group_4__1__Impl : ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) ) ;
    public final void rule__DifficultyConfig__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1404:1: ( ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) ) )
            // InternalMazeDsl.g:1405:1: ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:1405:1: ( ( rule__DifficultyConfig__InstantDeathAssignment_4_1 ) )
            // InternalMazeDsl.g:1406:2: ( rule__DifficultyConfig__InstantDeathAssignment_4_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathAssignment_4_1()); 
            // InternalMazeDsl.g:1407:2: ( rule__DifficultyConfig__InstantDeathAssignment_4_1 )
            // InternalMazeDsl.g:1407:3: rule__DifficultyConfig__InstantDeathAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__InstantDeathAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyConfigAccess().getInstantDeathAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_4__1__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_5__0"
    // InternalMazeDsl.g:1416:1: rule__DifficultyConfig__Group_5__0 : rule__DifficultyConfig__Group_5__0__Impl rule__DifficultyConfig__Group_5__1 ;
    public final void rule__DifficultyConfig__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1420:1: ( rule__DifficultyConfig__Group_5__0__Impl rule__DifficultyConfig__Group_5__1 )
            // InternalMazeDsl.g:1421:2: rule__DifficultyConfig__Group_5__0__Impl rule__DifficultyConfig__Group_5__1
            {
            pushFollow(FOLLOW_16);
            rule__DifficultyConfig__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_5__0"


    // $ANTLR start "rule__DifficultyConfig__Group_5__0__Impl"
    // InternalMazeDsl.g:1428:1: rule__DifficultyConfig__Group_5__0__Impl : ( 'speedMultiplier' ) ;
    public final void rule__DifficultyConfig__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1432:1: ( ( 'speedMultiplier' ) )
            // InternalMazeDsl.g:1433:1: ( 'speedMultiplier' )
            {
            // InternalMazeDsl.g:1433:1: ( 'speedMultiplier' )
            // InternalMazeDsl.g:1434:2: 'speedMultiplier'
            {
             before(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierKeyword_5_0()); 
            match(input,37,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_5__0__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_5__1"
    // InternalMazeDsl.g:1443:1: rule__DifficultyConfig__Group_5__1 : rule__DifficultyConfig__Group_5__1__Impl ;
    public final void rule__DifficultyConfig__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1447:1: ( rule__DifficultyConfig__Group_5__1__Impl )
            // InternalMazeDsl.g:1448:2: rule__DifficultyConfig__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_5__1"


    // $ANTLR start "rule__DifficultyConfig__Group_5__1__Impl"
    // InternalMazeDsl.g:1454:1: rule__DifficultyConfig__Group_5__1__Impl : ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) ) ;
    public final void rule__DifficultyConfig__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1458:1: ( ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) ) )
            // InternalMazeDsl.g:1459:1: ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:1459:1: ( ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 ) )
            // InternalMazeDsl.g:1460:2: ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierAssignment_5_1()); 
            // InternalMazeDsl.g:1461:2: ( rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 )
            // InternalMazeDsl.g:1461:3: rule__DifficultyConfig__SpeedMultiplierAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__SpeedMultiplierAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_5__1__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_6__0"
    // InternalMazeDsl.g:1470:1: rule__DifficultyConfig__Group_6__0 : rule__DifficultyConfig__Group_6__0__Impl rule__DifficultyConfig__Group_6__1 ;
    public final void rule__DifficultyConfig__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1474:1: ( rule__DifficultyConfig__Group_6__0__Impl rule__DifficultyConfig__Group_6__1 )
            // InternalMazeDsl.g:1475:2: rule__DifficultyConfig__Group_6__0__Impl rule__DifficultyConfig__Group_6__1
            {
            pushFollow(FOLLOW_16);
            rule__DifficultyConfig__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_6__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_6__0"


    // $ANTLR start "rule__DifficultyConfig__Group_6__0__Impl"
    // InternalMazeDsl.g:1482:1: rule__DifficultyConfig__Group_6__0__Impl : ( 'damageMultiplier' ) ;
    public final void rule__DifficultyConfig__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1486:1: ( ( 'damageMultiplier' ) )
            // InternalMazeDsl.g:1487:1: ( 'damageMultiplier' )
            {
            // InternalMazeDsl.g:1487:1: ( 'damageMultiplier' )
            // InternalMazeDsl.g:1488:2: 'damageMultiplier'
            {
             before(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierKeyword_6_0()); 
            match(input,38,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_6__0__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_6__1"
    // InternalMazeDsl.g:1497:1: rule__DifficultyConfig__Group_6__1 : rule__DifficultyConfig__Group_6__1__Impl ;
    public final void rule__DifficultyConfig__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1501:1: ( rule__DifficultyConfig__Group_6__1__Impl )
            // InternalMazeDsl.g:1502:2: rule__DifficultyConfig__Group_6__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_6__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_6__1"


    // $ANTLR start "rule__DifficultyConfig__Group_6__1__Impl"
    // InternalMazeDsl.g:1508:1: rule__DifficultyConfig__Group_6__1__Impl : ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) ) ;
    public final void rule__DifficultyConfig__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1512:1: ( ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) ) )
            // InternalMazeDsl.g:1513:1: ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:1513:1: ( ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 ) )
            // InternalMazeDsl.g:1514:2: ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierAssignment_6_1()); 
            // InternalMazeDsl.g:1515:2: ( rule__DifficultyConfig__DamageMultiplierAssignment_6_1 )
            // InternalMazeDsl.g:1515:3: rule__DifficultyConfig__DamageMultiplierAssignment_6_1
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__DamageMultiplierAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierAssignment_6_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_6__1__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_7__0"
    // InternalMazeDsl.g:1524:1: rule__DifficultyConfig__Group_7__0 : rule__DifficultyConfig__Group_7__0__Impl rule__DifficultyConfig__Group_7__1 ;
    public final void rule__DifficultyConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1528:1: ( rule__DifficultyConfig__Group_7__0__Impl rule__DifficultyConfig__Group_7__1 )
            // InternalMazeDsl.g:1529:2: rule__DifficultyConfig__Group_7__0__Impl rule__DifficultyConfig__Group_7__1
            {
            pushFollow(FOLLOW_16);
            rule__DifficultyConfig__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_7__0"


    // $ANTLR start "rule__DifficultyConfig__Group_7__0__Impl"
    // InternalMazeDsl.g:1536:1: rule__DifficultyConfig__Group_7__0__Impl : ( 'maxThreat' ) ;
    public final void rule__DifficultyConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1540:1: ( ( 'maxThreat' ) )
            // InternalMazeDsl.g:1541:1: ( 'maxThreat' )
            {
            // InternalMazeDsl.g:1541:1: ( 'maxThreat' )
            // InternalMazeDsl.g:1542:2: 'maxThreat'
            {
             before(grammarAccess.getDifficultyConfigAccess().getMaxThreatKeyword_7_0()); 
            match(input,39,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getMaxThreatKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_7__0__Impl"


    // $ANTLR start "rule__DifficultyConfig__Group_7__1"
    // InternalMazeDsl.g:1551:1: rule__DifficultyConfig__Group_7__1 : rule__DifficultyConfig__Group_7__1__Impl ;
    public final void rule__DifficultyConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1555:1: ( rule__DifficultyConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:1556:2: rule__DifficultyConfig__Group_7__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_7__1"


    // $ANTLR start "rule__DifficultyConfig__Group_7__1__Impl"
    // InternalMazeDsl.g:1562:1: rule__DifficultyConfig__Group_7__1__Impl : ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) ) ;
    public final void rule__DifficultyConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1566:1: ( ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) ) )
            // InternalMazeDsl.g:1567:1: ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:1567:1: ( ( rule__DifficultyConfig__MaxThreatAssignment_7_1 ) )
            // InternalMazeDsl.g:1568:2: ( rule__DifficultyConfig__MaxThreatAssignment_7_1 )
            {
             before(grammarAccess.getDifficultyConfigAccess().getMaxThreatAssignment_7_1()); 
            // InternalMazeDsl.g:1569:2: ( rule__DifficultyConfig__MaxThreatAssignment_7_1 )
            // InternalMazeDsl.g:1569:3: rule__DifficultyConfig__MaxThreatAssignment_7_1
            {
            pushFollow(FOLLOW_2);
            rule__DifficultyConfig__MaxThreatAssignment_7_1();

            state._fsp--;


            }

             after(grammarAccess.getDifficultyConfigAccess().getMaxThreatAssignment_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__Group_7__1__Impl"


    // $ANTLR start "rule__EnemyLimit__Group__0"
    // InternalMazeDsl.g:1578:1: rule__EnemyLimit__Group__0 : rule__EnemyLimit__Group__0__Impl rule__EnemyLimit__Group__1 ;
    public final void rule__EnemyLimit__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1582:1: ( rule__EnemyLimit__Group__0__Impl rule__EnemyLimit__Group__1 )
            // InternalMazeDsl.g:1583:2: rule__EnemyLimit__Group__0__Impl rule__EnemyLimit__Group__1
            {
            pushFollow(FOLLOW_17);
            rule__EnemyLimit__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EnemyLimit__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__0"


    // $ANTLR start "rule__EnemyLimit__Group__0__Impl"
    // InternalMazeDsl.g:1590:1: rule__EnemyLimit__Group__0__Impl : ( 'limit' ) ;
    public final void rule__EnemyLimit__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1594:1: ( ( 'limit' ) )
            // InternalMazeDsl.g:1595:1: ( 'limit' )
            {
            // InternalMazeDsl.g:1595:1: ( 'limit' )
            // InternalMazeDsl.g:1596:2: 'limit'
            {
             before(grammarAccess.getEnemyLimitAccess().getLimitKeyword_0()); 
            match(input,40,FOLLOW_2); 
             after(grammarAccess.getEnemyLimitAccess().getLimitKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__0__Impl"


    // $ANTLR start "rule__EnemyLimit__Group__1"
    // InternalMazeDsl.g:1605:1: rule__EnemyLimit__Group__1 : rule__EnemyLimit__Group__1__Impl rule__EnemyLimit__Group__2 ;
    public final void rule__EnemyLimit__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1609:1: ( rule__EnemyLimit__Group__1__Impl rule__EnemyLimit__Group__2 )
            // InternalMazeDsl.g:1610:2: rule__EnemyLimit__Group__1__Impl rule__EnemyLimit__Group__2
            {
            pushFollow(FOLLOW_18);
            rule__EnemyLimit__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EnemyLimit__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__1"


    // $ANTLR start "rule__EnemyLimit__Group__1__Impl"
    // InternalMazeDsl.g:1617:1: rule__EnemyLimit__Group__1__Impl : ( ( rule__EnemyLimit__TypeAssignment_1 ) ) ;
    public final void rule__EnemyLimit__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1621:1: ( ( ( rule__EnemyLimit__TypeAssignment_1 ) ) )
            // InternalMazeDsl.g:1622:1: ( ( rule__EnemyLimit__TypeAssignment_1 ) )
            {
            // InternalMazeDsl.g:1622:1: ( ( rule__EnemyLimit__TypeAssignment_1 ) )
            // InternalMazeDsl.g:1623:2: ( rule__EnemyLimit__TypeAssignment_1 )
            {
             before(grammarAccess.getEnemyLimitAccess().getTypeAssignment_1()); 
            // InternalMazeDsl.g:1624:2: ( rule__EnemyLimit__TypeAssignment_1 )
            // InternalMazeDsl.g:1624:3: rule__EnemyLimit__TypeAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__EnemyLimit__TypeAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getEnemyLimitAccess().getTypeAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__1__Impl"


    // $ANTLR start "rule__EnemyLimit__Group__2"
    // InternalMazeDsl.g:1632:1: rule__EnemyLimit__Group__2 : rule__EnemyLimit__Group__2__Impl rule__EnemyLimit__Group__3 ;
    public final void rule__EnemyLimit__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1636:1: ( rule__EnemyLimit__Group__2__Impl rule__EnemyLimit__Group__3 )
            // InternalMazeDsl.g:1637:2: rule__EnemyLimit__Group__2__Impl rule__EnemyLimit__Group__3
            {
            pushFollow(FOLLOW_16);
            rule__EnemyLimit__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__EnemyLimit__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__2"


    // $ANTLR start "rule__EnemyLimit__Group__2__Impl"
    // InternalMazeDsl.g:1644:1: rule__EnemyLimit__Group__2__Impl : ( 'max' ) ;
    public final void rule__EnemyLimit__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1648:1: ( ( 'max' ) )
            // InternalMazeDsl.g:1649:1: ( 'max' )
            {
            // InternalMazeDsl.g:1649:1: ( 'max' )
            // InternalMazeDsl.g:1650:2: 'max'
            {
             before(grammarAccess.getEnemyLimitAccess().getMaxKeyword_2()); 
            match(input,41,FOLLOW_2); 
             after(grammarAccess.getEnemyLimitAccess().getMaxKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__2__Impl"


    // $ANTLR start "rule__EnemyLimit__Group__3"
    // InternalMazeDsl.g:1659:1: rule__EnemyLimit__Group__3 : rule__EnemyLimit__Group__3__Impl ;
    public final void rule__EnemyLimit__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1663:1: ( rule__EnemyLimit__Group__3__Impl )
            // InternalMazeDsl.g:1664:2: rule__EnemyLimit__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__EnemyLimit__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__3"


    // $ANTLR start "rule__EnemyLimit__Group__3__Impl"
    // InternalMazeDsl.g:1670:1: rule__EnemyLimit__Group__3__Impl : ( ( rule__EnemyLimit__MaxCountAssignment_3 ) ) ;
    public final void rule__EnemyLimit__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1674:1: ( ( ( rule__EnemyLimit__MaxCountAssignment_3 ) ) )
            // InternalMazeDsl.g:1675:1: ( ( rule__EnemyLimit__MaxCountAssignment_3 ) )
            {
            // InternalMazeDsl.g:1675:1: ( ( rule__EnemyLimit__MaxCountAssignment_3 ) )
            // InternalMazeDsl.g:1676:2: ( rule__EnemyLimit__MaxCountAssignment_3 )
            {
             before(grammarAccess.getEnemyLimitAccess().getMaxCountAssignment_3()); 
            // InternalMazeDsl.g:1677:2: ( rule__EnemyLimit__MaxCountAssignment_3 )
            // InternalMazeDsl.g:1677:3: rule__EnemyLimit__MaxCountAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__EnemyLimit__MaxCountAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getEnemyLimitAccess().getMaxCountAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__Group__3__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__0"
    // InternalMazeDsl.g:1686:1: rule__OpponentConfig__Group__0 : rule__OpponentConfig__Group__0__Impl rule__OpponentConfig__Group__1 ;
    public final void rule__OpponentConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1690:1: ( rule__OpponentConfig__Group__0__Impl rule__OpponentConfig__Group__1 )
            // InternalMazeDsl.g:1691:2: rule__OpponentConfig__Group__0__Impl rule__OpponentConfig__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__OpponentConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__0"


    // $ANTLR start "rule__OpponentConfig__Group__0__Impl"
    // InternalMazeDsl.g:1698:1: rule__OpponentConfig__Group__0__Impl : ( 'opponent' ) ;
    public final void rule__OpponentConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1702:1: ( ( 'opponent' ) )
            // InternalMazeDsl.g:1703:1: ( 'opponent' )
            {
            // InternalMazeDsl.g:1703:1: ( 'opponent' )
            // InternalMazeDsl.g:1704:2: 'opponent'
            {
             before(grammarAccess.getOpponentConfigAccess().getOpponentKeyword_0()); 
            match(input,42,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getOpponentKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__1"
    // InternalMazeDsl.g:1713:1: rule__OpponentConfig__Group__1 : rule__OpponentConfig__Group__1__Impl rule__OpponentConfig__Group__2 ;
    public final void rule__OpponentConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1717:1: ( rule__OpponentConfig__Group__1__Impl rule__OpponentConfig__Group__2 )
            // InternalMazeDsl.g:1718:2: rule__OpponentConfig__Group__1__Impl rule__OpponentConfig__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__OpponentConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__1"


    // $ANTLR start "rule__OpponentConfig__Group__1__Impl"
    // InternalMazeDsl.g:1725:1: rule__OpponentConfig__Group__1__Impl : ( ( rule__OpponentConfig__NameAssignment_1 ) ) ;
    public final void rule__OpponentConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1729:1: ( ( ( rule__OpponentConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:1730:1: ( ( rule__OpponentConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:1730:1: ( ( rule__OpponentConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:1731:2: ( rule__OpponentConfig__NameAssignment_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:1732:2: ( rule__OpponentConfig__NameAssignment_1 )
            // InternalMazeDsl.g:1732:3: rule__OpponentConfig__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__2"
    // InternalMazeDsl.g:1740:1: rule__OpponentConfig__Group__2 : rule__OpponentConfig__Group__2__Impl rule__OpponentConfig__Group__3 ;
    public final void rule__OpponentConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1744:1: ( rule__OpponentConfig__Group__2__Impl rule__OpponentConfig__Group__3 )
            // InternalMazeDsl.g:1745:2: rule__OpponentConfig__Group__2__Impl rule__OpponentConfig__Group__3
            {
            pushFollow(FOLLOW_19);
            rule__OpponentConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__2"


    // $ANTLR start "rule__OpponentConfig__Group__2__Impl"
    // InternalMazeDsl.g:1752:1: rule__OpponentConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__OpponentConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1756:1: ( ( '{' ) )
            // InternalMazeDsl.g:1757:1: ( '{' )
            {
            // InternalMazeDsl.g:1757:1: ( '{' )
            // InternalMazeDsl.g:1758:2: '{'
            {
             before(grammarAccess.getOpponentConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__2__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__3"
    // InternalMazeDsl.g:1767:1: rule__OpponentConfig__Group__3 : rule__OpponentConfig__Group__3__Impl rule__OpponentConfig__Group__4 ;
    public final void rule__OpponentConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1771:1: ( rule__OpponentConfig__Group__3__Impl rule__OpponentConfig__Group__4 )
            // InternalMazeDsl.g:1772:2: rule__OpponentConfig__Group__3__Impl rule__OpponentConfig__Group__4
            {
            pushFollow(FOLLOW_17);
            rule__OpponentConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__3"


    // $ANTLR start "rule__OpponentConfig__Group__3__Impl"
    // InternalMazeDsl.g:1779:1: rule__OpponentConfig__Group__3__Impl : ( 'type' ) ;
    public final void rule__OpponentConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1783:1: ( ( 'type' ) )
            // InternalMazeDsl.g:1784:1: ( 'type' )
            {
            // InternalMazeDsl.g:1784:1: ( 'type' )
            // InternalMazeDsl.g:1785:2: 'type'
            {
             before(grammarAccess.getOpponentConfigAccess().getTypeKeyword_3()); 
            match(input,43,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getTypeKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__3__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__4"
    // InternalMazeDsl.g:1794:1: rule__OpponentConfig__Group__4 : rule__OpponentConfig__Group__4__Impl rule__OpponentConfig__Group__5 ;
    public final void rule__OpponentConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1798:1: ( rule__OpponentConfig__Group__4__Impl rule__OpponentConfig__Group__5 )
            // InternalMazeDsl.g:1799:2: rule__OpponentConfig__Group__4__Impl rule__OpponentConfig__Group__5
            {
            pushFollow(FOLLOW_20);
            rule__OpponentConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__4"


    // $ANTLR start "rule__OpponentConfig__Group__4__Impl"
    // InternalMazeDsl.g:1806:1: rule__OpponentConfig__Group__4__Impl : ( ( rule__OpponentConfig__TypeAssignment_4 ) ) ;
    public final void rule__OpponentConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1810:1: ( ( ( rule__OpponentConfig__TypeAssignment_4 ) ) )
            // InternalMazeDsl.g:1811:1: ( ( rule__OpponentConfig__TypeAssignment_4 ) )
            {
            // InternalMazeDsl.g:1811:1: ( ( rule__OpponentConfig__TypeAssignment_4 ) )
            // InternalMazeDsl.g:1812:2: ( rule__OpponentConfig__TypeAssignment_4 )
            {
             before(grammarAccess.getOpponentConfigAccess().getTypeAssignment_4()); 
            // InternalMazeDsl.g:1813:2: ( rule__OpponentConfig__TypeAssignment_4 )
            // InternalMazeDsl.g:1813:3: rule__OpponentConfig__TypeAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__TypeAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getTypeAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__4__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__5"
    // InternalMazeDsl.g:1821:1: rule__OpponentConfig__Group__5 : rule__OpponentConfig__Group__5__Impl rule__OpponentConfig__Group__6 ;
    public final void rule__OpponentConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1825:1: ( rule__OpponentConfig__Group__5__Impl rule__OpponentConfig__Group__6 )
            // InternalMazeDsl.g:1826:2: rule__OpponentConfig__Group__5__Impl rule__OpponentConfig__Group__6
            {
            pushFollow(FOLLOW_20);
            rule__OpponentConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__5"


    // $ANTLR start "rule__OpponentConfig__Group__5__Impl"
    // InternalMazeDsl.g:1833:1: rule__OpponentConfig__Group__5__Impl : ( ( rule__OpponentConfig__Group_5__0 )? ) ;
    public final void rule__OpponentConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1837:1: ( ( ( rule__OpponentConfig__Group_5__0 )? ) )
            // InternalMazeDsl.g:1838:1: ( ( rule__OpponentConfig__Group_5__0 )? )
            {
            // InternalMazeDsl.g:1838:1: ( ( rule__OpponentConfig__Group_5__0 )? )
            // InternalMazeDsl.g:1839:2: ( rule__OpponentConfig__Group_5__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_5()); 
            // InternalMazeDsl.g:1840:2: ( rule__OpponentConfig__Group_5__0 )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==44) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalMazeDsl.g:1840:3: rule__OpponentConfig__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__5__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__6"
    // InternalMazeDsl.g:1848:1: rule__OpponentConfig__Group__6 : rule__OpponentConfig__Group__6__Impl rule__OpponentConfig__Group__7 ;
    public final void rule__OpponentConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1852:1: ( rule__OpponentConfig__Group__6__Impl rule__OpponentConfig__Group__7 )
            // InternalMazeDsl.g:1853:2: rule__OpponentConfig__Group__6__Impl rule__OpponentConfig__Group__7
            {
            pushFollow(FOLLOW_20);
            rule__OpponentConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__6"


    // $ANTLR start "rule__OpponentConfig__Group__6__Impl"
    // InternalMazeDsl.g:1860:1: rule__OpponentConfig__Group__6__Impl : ( ( rule__OpponentConfig__Group_6__0 )? ) ;
    public final void rule__OpponentConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1864:1: ( ( ( rule__OpponentConfig__Group_6__0 )? ) )
            // InternalMazeDsl.g:1865:1: ( ( rule__OpponentConfig__Group_6__0 )? )
            {
            // InternalMazeDsl.g:1865:1: ( ( rule__OpponentConfig__Group_6__0 )? )
            // InternalMazeDsl.g:1866:2: ( rule__OpponentConfig__Group_6__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_6()); 
            // InternalMazeDsl.g:1867:2: ( rule__OpponentConfig__Group_6__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==45) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalMazeDsl.g:1867:3: rule__OpponentConfig__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__6__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__7"
    // InternalMazeDsl.g:1875:1: rule__OpponentConfig__Group__7 : rule__OpponentConfig__Group__7__Impl rule__OpponentConfig__Group__8 ;
    public final void rule__OpponentConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1879:1: ( rule__OpponentConfig__Group__7__Impl rule__OpponentConfig__Group__8 )
            // InternalMazeDsl.g:1880:2: rule__OpponentConfig__Group__7__Impl rule__OpponentConfig__Group__8
            {
            pushFollow(FOLLOW_20);
            rule__OpponentConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__7"


    // $ANTLR start "rule__OpponentConfig__Group__7__Impl"
    // InternalMazeDsl.g:1887:1: rule__OpponentConfig__Group__7__Impl : ( ( rule__OpponentConfig__Group_7__0 )? ) ;
    public final void rule__OpponentConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1891:1: ( ( ( rule__OpponentConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:1892:1: ( ( rule__OpponentConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:1892:1: ( ( rule__OpponentConfig__Group_7__0 )? )
            // InternalMazeDsl.g:1893:2: ( rule__OpponentConfig__Group_7__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:1894:2: ( rule__OpponentConfig__Group_7__0 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==46) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalMazeDsl.g:1894:3: rule__OpponentConfig__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__7__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__8"
    // InternalMazeDsl.g:1902:1: rule__OpponentConfig__Group__8 : rule__OpponentConfig__Group__8__Impl rule__OpponentConfig__Group__9 ;
    public final void rule__OpponentConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1906:1: ( rule__OpponentConfig__Group__8__Impl rule__OpponentConfig__Group__9 )
            // InternalMazeDsl.g:1907:2: rule__OpponentConfig__Group__8__Impl rule__OpponentConfig__Group__9
            {
            pushFollow(FOLLOW_20);
            rule__OpponentConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__8"


    // $ANTLR start "rule__OpponentConfig__Group__8__Impl"
    // InternalMazeDsl.g:1914:1: rule__OpponentConfig__Group__8__Impl : ( ( rule__OpponentConfig__Group_8__0 )? ) ;
    public final void rule__OpponentConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1918:1: ( ( ( rule__OpponentConfig__Group_8__0 )? ) )
            // InternalMazeDsl.g:1919:1: ( ( rule__OpponentConfig__Group_8__0 )? )
            {
            // InternalMazeDsl.g:1919:1: ( ( rule__OpponentConfig__Group_8__0 )? )
            // InternalMazeDsl.g:1920:2: ( rule__OpponentConfig__Group_8__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_8()); 
            // InternalMazeDsl.g:1921:2: ( rule__OpponentConfig__Group_8__0 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==47) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalMazeDsl.g:1921:3: rule__OpponentConfig__Group_8__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_8__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__8__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__9"
    // InternalMazeDsl.g:1929:1: rule__OpponentConfig__Group__9 : rule__OpponentConfig__Group__9__Impl rule__OpponentConfig__Group__10 ;
    public final void rule__OpponentConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1933:1: ( rule__OpponentConfig__Group__9__Impl rule__OpponentConfig__Group__10 )
            // InternalMazeDsl.g:1934:2: rule__OpponentConfig__Group__9__Impl rule__OpponentConfig__Group__10
            {
            pushFollow(FOLLOW_20);
            rule__OpponentConfig__Group__9__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__10();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__9"


    // $ANTLR start "rule__OpponentConfig__Group__9__Impl"
    // InternalMazeDsl.g:1941:1: rule__OpponentConfig__Group__9__Impl : ( ( rule__OpponentConfig__Group_9__0 )? ) ;
    public final void rule__OpponentConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1945:1: ( ( ( rule__OpponentConfig__Group_9__0 )? ) )
            // InternalMazeDsl.g:1946:1: ( ( rule__OpponentConfig__Group_9__0 )? )
            {
            // InternalMazeDsl.g:1946:1: ( ( rule__OpponentConfig__Group_9__0 )? )
            // InternalMazeDsl.g:1947:2: ( rule__OpponentConfig__Group_9__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_9()); 
            // InternalMazeDsl.g:1948:2: ( rule__OpponentConfig__Group_9__0 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==48) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalMazeDsl.g:1948:3: rule__OpponentConfig__Group_9__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_9__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__9__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__10"
    // InternalMazeDsl.g:1956:1: rule__OpponentConfig__Group__10 : rule__OpponentConfig__Group__10__Impl rule__OpponentConfig__Group__11 ;
    public final void rule__OpponentConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1960:1: ( rule__OpponentConfig__Group__10__Impl rule__OpponentConfig__Group__11 )
            // InternalMazeDsl.g:1961:2: rule__OpponentConfig__Group__10__Impl rule__OpponentConfig__Group__11
            {
            pushFollow(FOLLOW_20);
            rule__OpponentConfig__Group__10__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__11();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__10"


    // $ANTLR start "rule__OpponentConfig__Group__10__Impl"
    // InternalMazeDsl.g:1968:1: rule__OpponentConfig__Group__10__Impl : ( ( rule__OpponentConfig__Group_10__0 )? ) ;
    public final void rule__OpponentConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1972:1: ( ( ( rule__OpponentConfig__Group_10__0 )? ) )
            // InternalMazeDsl.g:1973:1: ( ( rule__OpponentConfig__Group_10__0 )? )
            {
            // InternalMazeDsl.g:1973:1: ( ( rule__OpponentConfig__Group_10__0 )? )
            // InternalMazeDsl.g:1974:2: ( rule__OpponentConfig__Group_10__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_10()); 
            // InternalMazeDsl.g:1975:2: ( rule__OpponentConfig__Group_10__0 )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==49) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalMazeDsl.g:1975:3: rule__OpponentConfig__Group_10__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_10__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_10()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__10__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__11"
    // InternalMazeDsl.g:1983:1: rule__OpponentConfig__Group__11 : rule__OpponentConfig__Group__11__Impl rule__OpponentConfig__Group__12 ;
    public final void rule__OpponentConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1987:1: ( rule__OpponentConfig__Group__11__Impl rule__OpponentConfig__Group__12 )
            // InternalMazeDsl.g:1988:2: rule__OpponentConfig__Group__11__Impl rule__OpponentConfig__Group__12
            {
            pushFollow(FOLLOW_20);
            rule__OpponentConfig__Group__11__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__12();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__11"


    // $ANTLR start "rule__OpponentConfig__Group__11__Impl"
    // InternalMazeDsl.g:1995:1: rule__OpponentConfig__Group__11__Impl : ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? ) ;
    public final void rule__OpponentConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:1999:1: ( ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? ) )
            // InternalMazeDsl.g:2000:1: ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? )
            {
            // InternalMazeDsl.g:2000:1: ( ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )? )
            // InternalMazeDsl.g:2001:2: ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsAssignment_11()); 
            // InternalMazeDsl.g:2002:2: ( rule__OpponentConfig__CharacterSpecificsAssignment_11 )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==51||LA25_0==55||LA25_0==58) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalMazeDsl.g:2002:3: rule__OpponentConfig__CharacterSpecificsAssignment_11
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__CharacterSpecificsAssignment_11();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsAssignment_11()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__11__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__12"
    // InternalMazeDsl.g:2010:1: rule__OpponentConfig__Group__12 : rule__OpponentConfig__Group__12__Impl rule__OpponentConfig__Group__13 ;
    public final void rule__OpponentConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2014:1: ( rule__OpponentConfig__Group__12__Impl rule__OpponentConfig__Group__13 )
            // InternalMazeDsl.g:2015:2: rule__OpponentConfig__Group__12__Impl rule__OpponentConfig__Group__13
            {
            pushFollow(FOLLOW_20);
            rule__OpponentConfig__Group__12__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__13();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__12"


    // $ANTLR start "rule__OpponentConfig__Group__12__Impl"
    // InternalMazeDsl.g:2022:1: rule__OpponentConfig__Group__12__Impl : ( ( rule__OpponentConfig__Group_12__0 )? ) ;
    public final void rule__OpponentConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2026:1: ( ( ( rule__OpponentConfig__Group_12__0 )? ) )
            // InternalMazeDsl.g:2027:1: ( ( rule__OpponentConfig__Group_12__0 )? )
            {
            // InternalMazeDsl.g:2027:1: ( ( rule__OpponentConfig__Group_12__0 )? )
            // InternalMazeDsl.g:2028:2: ( rule__OpponentConfig__Group_12__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_12()); 
            // InternalMazeDsl.g:2029:2: ( rule__OpponentConfig__Group_12__0 )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==22) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalMazeDsl.g:2029:3: rule__OpponentConfig__Group_12__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_12__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_12()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__12__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__13"
    // InternalMazeDsl.g:2037:1: rule__OpponentConfig__Group__13 : rule__OpponentConfig__Group__13__Impl rule__OpponentConfig__Group__14 ;
    public final void rule__OpponentConfig__Group__13() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2041:1: ( rule__OpponentConfig__Group__13__Impl rule__OpponentConfig__Group__14 )
            // InternalMazeDsl.g:2042:2: rule__OpponentConfig__Group__13__Impl rule__OpponentConfig__Group__14
            {
            pushFollow(FOLLOW_20);
            rule__OpponentConfig__Group__13__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__14();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__13"


    // $ANTLR start "rule__OpponentConfig__Group__13__Impl"
    // InternalMazeDsl.g:2049:1: rule__OpponentConfig__Group__13__Impl : ( ( rule__OpponentConfig__Group_13__0 )? ) ;
    public final void rule__OpponentConfig__Group__13__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2053:1: ( ( ( rule__OpponentConfig__Group_13__0 )? ) )
            // InternalMazeDsl.g:2054:1: ( ( rule__OpponentConfig__Group_13__0 )? )
            {
            // InternalMazeDsl.g:2054:1: ( ( rule__OpponentConfig__Group_13__0 )? )
            // InternalMazeDsl.g:2055:2: ( rule__OpponentConfig__Group_13__0 )?
            {
             before(grammarAccess.getOpponentConfigAccess().getGroup_13()); 
            // InternalMazeDsl.g:2056:2: ( rule__OpponentConfig__Group_13__0 )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==50) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalMazeDsl.g:2056:3: rule__OpponentConfig__Group_13__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__OpponentConfig__Group_13__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getOpponentConfigAccess().getGroup_13()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__13__Impl"


    // $ANTLR start "rule__OpponentConfig__Group__14"
    // InternalMazeDsl.g:2064:1: rule__OpponentConfig__Group__14 : rule__OpponentConfig__Group__14__Impl ;
    public final void rule__OpponentConfig__Group__14() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2068:1: ( rule__OpponentConfig__Group__14__Impl )
            // InternalMazeDsl.g:2069:2: rule__OpponentConfig__Group__14__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group__14__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__14"


    // $ANTLR start "rule__OpponentConfig__Group__14__Impl"
    // InternalMazeDsl.g:2075:1: rule__OpponentConfig__Group__14__Impl : ( '}' ) ;
    public final void rule__OpponentConfig__Group__14__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2079:1: ( ( '}' ) )
            // InternalMazeDsl.g:2080:1: ( '}' )
            {
            // InternalMazeDsl.g:2080:1: ( '}' )
            // InternalMazeDsl.g:2081:2: '}'
            {
             before(grammarAccess.getOpponentConfigAccess().getRightCurlyBracketKeyword_14()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getRightCurlyBracketKeyword_14()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group__14__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_5__0"
    // InternalMazeDsl.g:2091:1: rule__OpponentConfig__Group_5__0 : rule__OpponentConfig__Group_5__0__Impl rule__OpponentConfig__Group_5__1 ;
    public final void rule__OpponentConfig__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2095:1: ( rule__OpponentConfig__Group_5__0__Impl rule__OpponentConfig__Group_5__1 )
            // InternalMazeDsl.g:2096:2: rule__OpponentConfig__Group_5__0__Impl rule__OpponentConfig__Group_5__1
            {
            pushFollow(FOLLOW_10);
            rule__OpponentConfig__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_5__0"


    // $ANTLR start "rule__OpponentConfig__Group_5__0__Impl"
    // InternalMazeDsl.g:2103:1: rule__OpponentConfig__Group_5__0__Impl : ( 'displayName' ) ;
    public final void rule__OpponentConfig__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2107:1: ( ( 'displayName' ) )
            // InternalMazeDsl.g:2108:1: ( 'displayName' )
            {
            // InternalMazeDsl.g:2108:1: ( 'displayName' )
            // InternalMazeDsl.g:2109:2: 'displayName'
            {
             before(grammarAccess.getOpponentConfigAccess().getDisplayNameKeyword_5_0()); 
            match(input,44,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getDisplayNameKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_5__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_5__1"
    // InternalMazeDsl.g:2118:1: rule__OpponentConfig__Group_5__1 : rule__OpponentConfig__Group_5__1__Impl ;
    public final void rule__OpponentConfig__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2122:1: ( rule__OpponentConfig__Group_5__1__Impl )
            // InternalMazeDsl.g:2123:2: rule__OpponentConfig__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_5__1"


    // $ANTLR start "rule__OpponentConfig__Group_5__1__Impl"
    // InternalMazeDsl.g:2129:1: rule__OpponentConfig__Group_5__1__Impl : ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) ) ;
    public final void rule__OpponentConfig__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2133:1: ( ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) ) )
            // InternalMazeDsl.g:2134:1: ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:2134:1: ( ( rule__OpponentConfig__DisplayNameAssignment_5_1 ) )
            // InternalMazeDsl.g:2135:2: ( rule__OpponentConfig__DisplayNameAssignment_5_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getDisplayNameAssignment_5_1()); 
            // InternalMazeDsl.g:2136:2: ( rule__OpponentConfig__DisplayNameAssignment_5_1 )
            // InternalMazeDsl.g:2136:3: rule__OpponentConfig__DisplayNameAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__DisplayNameAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getDisplayNameAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_5__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_6__0"
    // InternalMazeDsl.g:2145:1: rule__OpponentConfig__Group_6__0 : rule__OpponentConfig__Group_6__0__Impl rule__OpponentConfig__Group_6__1 ;
    public final void rule__OpponentConfig__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2149:1: ( rule__OpponentConfig__Group_6__0__Impl rule__OpponentConfig__Group_6__1 )
            // InternalMazeDsl.g:2150:2: rule__OpponentConfig__Group_6__0__Impl rule__OpponentConfig__Group_6__1
            {
            pushFollow(FOLLOW_16);
            rule__OpponentConfig__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_6__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_6__0"


    // $ANTLR start "rule__OpponentConfig__Group_6__0__Impl"
    // InternalMazeDsl.g:2157:1: rule__OpponentConfig__Group_6__0__Impl : ( 'health' ) ;
    public final void rule__OpponentConfig__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2161:1: ( ( 'health' ) )
            // InternalMazeDsl.g:2162:1: ( 'health' )
            {
            // InternalMazeDsl.g:2162:1: ( 'health' )
            // InternalMazeDsl.g:2163:2: 'health'
            {
             before(grammarAccess.getOpponentConfigAccess().getHealthKeyword_6_0()); 
            match(input,45,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getHealthKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_6__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_6__1"
    // InternalMazeDsl.g:2172:1: rule__OpponentConfig__Group_6__1 : rule__OpponentConfig__Group_6__1__Impl ;
    public final void rule__OpponentConfig__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2176:1: ( rule__OpponentConfig__Group_6__1__Impl )
            // InternalMazeDsl.g:2177:2: rule__OpponentConfig__Group_6__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_6__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_6__1"


    // $ANTLR start "rule__OpponentConfig__Group_6__1__Impl"
    // InternalMazeDsl.g:2183:1: rule__OpponentConfig__Group_6__1__Impl : ( ( rule__OpponentConfig__HealthAssignment_6_1 ) ) ;
    public final void rule__OpponentConfig__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2187:1: ( ( ( rule__OpponentConfig__HealthAssignment_6_1 ) ) )
            // InternalMazeDsl.g:2188:1: ( ( rule__OpponentConfig__HealthAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:2188:1: ( ( rule__OpponentConfig__HealthAssignment_6_1 ) )
            // InternalMazeDsl.g:2189:2: ( rule__OpponentConfig__HealthAssignment_6_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getHealthAssignment_6_1()); 
            // InternalMazeDsl.g:2190:2: ( rule__OpponentConfig__HealthAssignment_6_1 )
            // InternalMazeDsl.g:2190:3: rule__OpponentConfig__HealthAssignment_6_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__HealthAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getHealthAssignment_6_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_6__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_7__0"
    // InternalMazeDsl.g:2199:1: rule__OpponentConfig__Group_7__0 : rule__OpponentConfig__Group_7__0__Impl rule__OpponentConfig__Group_7__1 ;
    public final void rule__OpponentConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2203:1: ( rule__OpponentConfig__Group_7__0__Impl rule__OpponentConfig__Group_7__1 )
            // InternalMazeDsl.g:2204:2: rule__OpponentConfig__Group_7__0__Impl rule__OpponentConfig__Group_7__1
            {
            pushFollow(FOLLOW_16);
            rule__OpponentConfig__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_7__0"


    // $ANTLR start "rule__OpponentConfig__Group_7__0__Impl"
    // InternalMazeDsl.g:2211:1: rule__OpponentConfig__Group_7__0__Impl : ( 'speed' ) ;
    public final void rule__OpponentConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2215:1: ( ( 'speed' ) )
            // InternalMazeDsl.g:2216:1: ( 'speed' )
            {
            // InternalMazeDsl.g:2216:1: ( 'speed' )
            // InternalMazeDsl.g:2217:2: 'speed'
            {
             before(grammarAccess.getOpponentConfigAccess().getSpeedKeyword_7_0()); 
            match(input,46,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getSpeedKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_7__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_7__1"
    // InternalMazeDsl.g:2226:1: rule__OpponentConfig__Group_7__1 : rule__OpponentConfig__Group_7__1__Impl ;
    public final void rule__OpponentConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2230:1: ( rule__OpponentConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:2231:2: rule__OpponentConfig__Group_7__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_7__1"


    // $ANTLR start "rule__OpponentConfig__Group_7__1__Impl"
    // InternalMazeDsl.g:2237:1: rule__OpponentConfig__Group_7__1__Impl : ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) ) ;
    public final void rule__OpponentConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2241:1: ( ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) ) )
            // InternalMazeDsl.g:2242:1: ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:2242:1: ( ( rule__OpponentConfig__SpeedAssignment_7_1 ) )
            // InternalMazeDsl.g:2243:2: ( rule__OpponentConfig__SpeedAssignment_7_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getSpeedAssignment_7_1()); 
            // InternalMazeDsl.g:2244:2: ( rule__OpponentConfig__SpeedAssignment_7_1 )
            // InternalMazeDsl.g:2244:3: rule__OpponentConfig__SpeedAssignment_7_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__SpeedAssignment_7_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getSpeedAssignment_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_7__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_8__0"
    // InternalMazeDsl.g:2253:1: rule__OpponentConfig__Group_8__0 : rule__OpponentConfig__Group_8__0__Impl rule__OpponentConfig__Group_8__1 ;
    public final void rule__OpponentConfig__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2257:1: ( rule__OpponentConfig__Group_8__0__Impl rule__OpponentConfig__Group_8__1 )
            // InternalMazeDsl.g:2258:2: rule__OpponentConfig__Group_8__0__Impl rule__OpponentConfig__Group_8__1
            {
            pushFollow(FOLLOW_16);
            rule__OpponentConfig__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_8__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_8__0"


    // $ANTLR start "rule__OpponentConfig__Group_8__0__Impl"
    // InternalMazeDsl.g:2265:1: rule__OpponentConfig__Group_8__0__Impl : ( 'threatLevel' ) ;
    public final void rule__OpponentConfig__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2269:1: ( ( 'threatLevel' ) )
            // InternalMazeDsl.g:2270:1: ( 'threatLevel' )
            {
            // InternalMazeDsl.g:2270:1: ( 'threatLevel' )
            // InternalMazeDsl.g:2271:2: 'threatLevel'
            {
             before(grammarAccess.getOpponentConfigAccess().getThreatLevelKeyword_8_0()); 
            match(input,47,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getThreatLevelKeyword_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_8__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_8__1"
    // InternalMazeDsl.g:2280:1: rule__OpponentConfig__Group_8__1 : rule__OpponentConfig__Group_8__1__Impl ;
    public final void rule__OpponentConfig__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2284:1: ( rule__OpponentConfig__Group_8__1__Impl )
            // InternalMazeDsl.g:2285:2: rule__OpponentConfig__Group_8__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_8__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_8__1"


    // $ANTLR start "rule__OpponentConfig__Group_8__1__Impl"
    // InternalMazeDsl.g:2291:1: rule__OpponentConfig__Group_8__1__Impl : ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) ) ;
    public final void rule__OpponentConfig__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2295:1: ( ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) ) )
            // InternalMazeDsl.g:2296:1: ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:2296:1: ( ( rule__OpponentConfig__ThreatLevelAssignment_8_1 ) )
            // InternalMazeDsl.g:2297:2: ( rule__OpponentConfig__ThreatLevelAssignment_8_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getThreatLevelAssignment_8_1()); 
            // InternalMazeDsl.g:2298:2: ( rule__OpponentConfig__ThreatLevelAssignment_8_1 )
            // InternalMazeDsl.g:2298:3: rule__OpponentConfig__ThreatLevelAssignment_8_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__ThreatLevelAssignment_8_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getThreatLevelAssignment_8_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_8__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_9__0"
    // InternalMazeDsl.g:2307:1: rule__OpponentConfig__Group_9__0 : rule__OpponentConfig__Group_9__0__Impl rule__OpponentConfig__Group_9__1 ;
    public final void rule__OpponentConfig__Group_9__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2311:1: ( rule__OpponentConfig__Group_9__0__Impl rule__OpponentConfig__Group_9__1 )
            // InternalMazeDsl.g:2312:2: rule__OpponentConfig__Group_9__0__Impl rule__OpponentConfig__Group_9__1
            {
            pushFollow(FOLLOW_21);
            rule__OpponentConfig__Group_9__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_9__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_9__0"


    // $ANTLR start "rule__OpponentConfig__Group_9__0__Impl"
    // InternalMazeDsl.g:2319:1: rule__OpponentConfig__Group_9__0__Impl : ( 'enabled' ) ;
    public final void rule__OpponentConfig__Group_9__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2323:1: ( ( 'enabled' ) )
            // InternalMazeDsl.g:2324:1: ( 'enabled' )
            {
            // InternalMazeDsl.g:2324:1: ( 'enabled' )
            // InternalMazeDsl.g:2325:2: 'enabled'
            {
             before(grammarAccess.getOpponentConfigAccess().getEnabledKeyword_9_0()); 
            match(input,48,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getEnabledKeyword_9_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_9__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_9__1"
    // InternalMazeDsl.g:2334:1: rule__OpponentConfig__Group_9__1 : rule__OpponentConfig__Group_9__1__Impl ;
    public final void rule__OpponentConfig__Group_9__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2338:1: ( rule__OpponentConfig__Group_9__1__Impl )
            // InternalMazeDsl.g:2339:2: rule__OpponentConfig__Group_9__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_9__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_9__1"


    // $ANTLR start "rule__OpponentConfig__Group_9__1__Impl"
    // InternalMazeDsl.g:2345:1: rule__OpponentConfig__Group_9__1__Impl : ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) ) ;
    public final void rule__OpponentConfig__Group_9__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2349:1: ( ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) ) )
            // InternalMazeDsl.g:2350:1: ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) )
            {
            // InternalMazeDsl.g:2350:1: ( ( rule__OpponentConfig__EnabledAssignment_9_1 ) )
            // InternalMazeDsl.g:2351:2: ( rule__OpponentConfig__EnabledAssignment_9_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getEnabledAssignment_9_1()); 
            // InternalMazeDsl.g:2352:2: ( rule__OpponentConfig__EnabledAssignment_9_1 )
            // InternalMazeDsl.g:2352:3: rule__OpponentConfig__EnabledAssignment_9_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__EnabledAssignment_9_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getEnabledAssignment_9_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_9__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_10__0"
    // InternalMazeDsl.g:2361:1: rule__OpponentConfig__Group_10__0 : rule__OpponentConfig__Group_10__0__Impl rule__OpponentConfig__Group_10__1 ;
    public final void rule__OpponentConfig__Group_10__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2365:1: ( rule__OpponentConfig__Group_10__0__Impl rule__OpponentConfig__Group_10__1 )
            // InternalMazeDsl.g:2366:2: rule__OpponentConfig__Group_10__0__Impl rule__OpponentConfig__Group_10__1
            {
            pushFollow(FOLLOW_22);
            rule__OpponentConfig__Group_10__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_10__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_10__0"


    // $ANTLR start "rule__OpponentConfig__Group_10__0__Impl"
    // InternalMazeDsl.g:2373:1: rule__OpponentConfig__Group_10__0__Impl : ( 'behavior' ) ;
    public final void rule__OpponentConfig__Group_10__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2377:1: ( ( 'behavior' ) )
            // InternalMazeDsl.g:2378:1: ( 'behavior' )
            {
            // InternalMazeDsl.g:2378:1: ( 'behavior' )
            // InternalMazeDsl.g:2379:2: 'behavior'
            {
             before(grammarAccess.getOpponentConfigAccess().getBehaviorKeyword_10_0()); 
            match(input,49,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getBehaviorKeyword_10_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_10__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_10__1"
    // InternalMazeDsl.g:2388:1: rule__OpponentConfig__Group_10__1 : rule__OpponentConfig__Group_10__1__Impl ;
    public final void rule__OpponentConfig__Group_10__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2392:1: ( rule__OpponentConfig__Group_10__1__Impl )
            // InternalMazeDsl.g:2393:2: rule__OpponentConfig__Group_10__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_10__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_10__1"


    // $ANTLR start "rule__OpponentConfig__Group_10__1__Impl"
    // InternalMazeDsl.g:2399:1: rule__OpponentConfig__Group_10__1__Impl : ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) ) ;
    public final void rule__OpponentConfig__Group_10__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2403:1: ( ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) ) )
            // InternalMazeDsl.g:2404:1: ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) )
            {
            // InternalMazeDsl.g:2404:1: ( ( rule__OpponentConfig__BehaviorAssignment_10_1 ) )
            // InternalMazeDsl.g:2405:2: ( rule__OpponentConfig__BehaviorAssignment_10_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getBehaviorAssignment_10_1()); 
            // InternalMazeDsl.g:2406:2: ( rule__OpponentConfig__BehaviorAssignment_10_1 )
            // InternalMazeDsl.g:2406:3: rule__OpponentConfig__BehaviorAssignment_10_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__BehaviorAssignment_10_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getBehaviorAssignment_10_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_10__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_12__0"
    // InternalMazeDsl.g:2415:1: rule__OpponentConfig__Group_12__0 : rule__OpponentConfig__Group_12__0__Impl rule__OpponentConfig__Group_12__1 ;
    public final void rule__OpponentConfig__Group_12__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2419:1: ( rule__OpponentConfig__Group_12__0__Impl rule__OpponentConfig__Group_12__1 )
            // InternalMazeDsl.g:2420:2: rule__OpponentConfig__Group_12__0__Impl rule__OpponentConfig__Group_12__1
            {
            pushFollow(FOLLOW_3);
            rule__OpponentConfig__Group_12__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_12__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_12__0"


    // $ANTLR start "rule__OpponentConfig__Group_12__0__Impl"
    // InternalMazeDsl.g:2427:1: rule__OpponentConfig__Group_12__0__Impl : ( 'patrol' ) ;
    public final void rule__OpponentConfig__Group_12__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2431:1: ( ( 'patrol' ) )
            // InternalMazeDsl.g:2432:1: ( 'patrol' )
            {
            // InternalMazeDsl.g:2432:1: ( 'patrol' )
            // InternalMazeDsl.g:2433:2: 'patrol'
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolKeyword_12_0()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getPatrolKeyword_12_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_12__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_12__1"
    // InternalMazeDsl.g:2442:1: rule__OpponentConfig__Group_12__1 : rule__OpponentConfig__Group_12__1__Impl ;
    public final void rule__OpponentConfig__Group_12__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2446:1: ( rule__OpponentConfig__Group_12__1__Impl )
            // InternalMazeDsl.g:2447:2: rule__OpponentConfig__Group_12__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_12__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_12__1"


    // $ANTLR start "rule__OpponentConfig__Group_12__1__Impl"
    // InternalMazeDsl.g:2453:1: rule__OpponentConfig__Group_12__1__Impl : ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) ) ;
    public final void rule__OpponentConfig__Group_12__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2457:1: ( ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) ) )
            // InternalMazeDsl.g:2458:1: ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) )
            {
            // InternalMazeDsl.g:2458:1: ( ( rule__OpponentConfig__PatrolRefAssignment_12_1 ) )
            // InternalMazeDsl.g:2459:2: ( rule__OpponentConfig__PatrolRefAssignment_12_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolRefAssignment_12_1()); 
            // InternalMazeDsl.g:2460:2: ( rule__OpponentConfig__PatrolRefAssignment_12_1 )
            // InternalMazeDsl.g:2460:3: rule__OpponentConfig__PatrolRefAssignment_12_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__PatrolRefAssignment_12_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getPatrolRefAssignment_12_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_12__1__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_13__0"
    // InternalMazeDsl.g:2469:1: rule__OpponentConfig__Group_13__0 : rule__OpponentConfig__Group_13__0__Impl rule__OpponentConfig__Group_13__1 ;
    public final void rule__OpponentConfig__Group_13__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2473:1: ( rule__OpponentConfig__Group_13__0__Impl rule__OpponentConfig__Group_13__1 )
            // InternalMazeDsl.g:2474:2: rule__OpponentConfig__Group_13__0__Impl rule__OpponentConfig__Group_13__1
            {
            pushFollow(FOLLOW_3);
            rule__OpponentConfig__Group_13__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_13__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_13__0"


    // $ANTLR start "rule__OpponentConfig__Group_13__0__Impl"
    // InternalMazeDsl.g:2481:1: rule__OpponentConfig__Group_13__0__Impl : ( 'loot' ) ;
    public final void rule__OpponentConfig__Group_13__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2485:1: ( ( 'loot' ) )
            // InternalMazeDsl.g:2486:1: ( 'loot' )
            {
            // InternalMazeDsl.g:2486:1: ( 'loot' )
            // InternalMazeDsl.g:2487:2: 'loot'
            {
             before(grammarAccess.getOpponentConfigAccess().getLootKeyword_13_0()); 
            match(input,50,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getLootKeyword_13_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_13__0__Impl"


    // $ANTLR start "rule__OpponentConfig__Group_13__1"
    // InternalMazeDsl.g:2496:1: rule__OpponentConfig__Group_13__1 : rule__OpponentConfig__Group_13__1__Impl ;
    public final void rule__OpponentConfig__Group_13__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2500:1: ( rule__OpponentConfig__Group_13__1__Impl )
            // InternalMazeDsl.g:2501:2: rule__OpponentConfig__Group_13__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__Group_13__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_13__1"


    // $ANTLR start "rule__OpponentConfig__Group_13__1__Impl"
    // InternalMazeDsl.g:2507:1: rule__OpponentConfig__Group_13__1__Impl : ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) ) ;
    public final void rule__OpponentConfig__Group_13__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2511:1: ( ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) ) )
            // InternalMazeDsl.g:2512:1: ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) )
            {
            // InternalMazeDsl.g:2512:1: ( ( rule__OpponentConfig__LootRefAssignment_13_1 ) )
            // InternalMazeDsl.g:2513:2: ( rule__OpponentConfig__LootRefAssignment_13_1 )
            {
             before(grammarAccess.getOpponentConfigAccess().getLootRefAssignment_13_1()); 
            // InternalMazeDsl.g:2514:2: ( rule__OpponentConfig__LootRefAssignment_13_1 )
            // InternalMazeDsl.g:2514:3: rule__OpponentConfig__LootRefAssignment_13_1
            {
            pushFollow(FOLLOW_2);
            rule__OpponentConfig__LootRefAssignment_13_1();

            state._fsp--;


            }

             after(grammarAccess.getOpponentConfigAccess().getLootRefAssignment_13_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__Group_13__1__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__0"
    // InternalMazeDsl.g:2523:1: rule__ZombieSpecifics__Group__0 : rule__ZombieSpecifics__Group__0__Impl rule__ZombieSpecifics__Group__1 ;
    public final void rule__ZombieSpecifics__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2527:1: ( rule__ZombieSpecifics__Group__0__Impl rule__ZombieSpecifics__Group__1 )
            // InternalMazeDsl.g:2528:2: rule__ZombieSpecifics__Group__0__Impl rule__ZombieSpecifics__Group__1
            {
            pushFollow(FOLLOW_23);
            rule__ZombieSpecifics__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__0"


    // $ANTLR start "rule__ZombieSpecifics__Group__0__Impl"
    // InternalMazeDsl.g:2535:1: rule__ZombieSpecifics__Group__0__Impl : ( () ) ;
    public final void rule__ZombieSpecifics__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2539:1: ( ( () ) )
            // InternalMazeDsl.g:2540:1: ( () )
            {
            // InternalMazeDsl.g:2540:1: ( () )
            // InternalMazeDsl.g:2541:2: ()
            {
             before(grammarAccess.getZombieSpecificsAccess().getZombieSpecificsAction_0()); 
            // InternalMazeDsl.g:2542:2: ()
            // InternalMazeDsl.g:2542:3: 
            {
            }

             after(grammarAccess.getZombieSpecificsAccess().getZombieSpecificsAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__0__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__1"
    // InternalMazeDsl.g:2550:1: rule__ZombieSpecifics__Group__1 : rule__ZombieSpecifics__Group__1__Impl rule__ZombieSpecifics__Group__2 ;
    public final void rule__ZombieSpecifics__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2554:1: ( rule__ZombieSpecifics__Group__1__Impl rule__ZombieSpecifics__Group__2 )
            // InternalMazeDsl.g:2555:2: rule__ZombieSpecifics__Group__1__Impl rule__ZombieSpecifics__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__ZombieSpecifics__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__1"


    // $ANTLR start "rule__ZombieSpecifics__Group__1__Impl"
    // InternalMazeDsl.g:2562:1: rule__ZombieSpecifics__Group__1__Impl : ( 'zombie-stats' ) ;
    public final void rule__ZombieSpecifics__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2566:1: ( ( 'zombie-stats' ) )
            // InternalMazeDsl.g:2567:1: ( 'zombie-stats' )
            {
            // InternalMazeDsl.g:2567:1: ( 'zombie-stats' )
            // InternalMazeDsl.g:2568:2: 'zombie-stats'
            {
             before(grammarAccess.getZombieSpecificsAccess().getZombieStatsKeyword_1()); 
            match(input,51,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getZombieStatsKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__1__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__2"
    // InternalMazeDsl.g:2577:1: rule__ZombieSpecifics__Group__2 : rule__ZombieSpecifics__Group__2__Impl rule__ZombieSpecifics__Group__3 ;
    public final void rule__ZombieSpecifics__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2581:1: ( rule__ZombieSpecifics__Group__2__Impl rule__ZombieSpecifics__Group__3 )
            // InternalMazeDsl.g:2582:2: rule__ZombieSpecifics__Group__2__Impl rule__ZombieSpecifics__Group__3
            {
            pushFollow(FOLLOW_24);
            rule__ZombieSpecifics__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__2"


    // $ANTLR start "rule__ZombieSpecifics__Group__2__Impl"
    // InternalMazeDsl.g:2589:1: rule__ZombieSpecifics__Group__2__Impl : ( '{' ) ;
    public final void rule__ZombieSpecifics__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2593:1: ( ( '{' ) )
            // InternalMazeDsl.g:2594:1: ( '{' )
            {
            // InternalMazeDsl.g:2594:1: ( '{' )
            // InternalMazeDsl.g:2595:2: '{'
            {
             before(grammarAccess.getZombieSpecificsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__2__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__3"
    // InternalMazeDsl.g:2604:1: rule__ZombieSpecifics__Group__3 : rule__ZombieSpecifics__Group__3__Impl rule__ZombieSpecifics__Group__4 ;
    public final void rule__ZombieSpecifics__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2608:1: ( rule__ZombieSpecifics__Group__3__Impl rule__ZombieSpecifics__Group__4 )
            // InternalMazeDsl.g:2609:2: rule__ZombieSpecifics__Group__3__Impl rule__ZombieSpecifics__Group__4
            {
            pushFollow(FOLLOW_24);
            rule__ZombieSpecifics__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__3"


    // $ANTLR start "rule__ZombieSpecifics__Group__3__Impl"
    // InternalMazeDsl.g:2616:1: rule__ZombieSpecifics__Group__3__Impl : ( ( rule__ZombieSpecifics__Group_3__0 )? ) ;
    public final void rule__ZombieSpecifics__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2620:1: ( ( ( rule__ZombieSpecifics__Group_3__0 )? ) )
            // InternalMazeDsl.g:2621:1: ( ( rule__ZombieSpecifics__Group_3__0 )? )
            {
            // InternalMazeDsl.g:2621:1: ( ( rule__ZombieSpecifics__Group_3__0 )? )
            // InternalMazeDsl.g:2622:2: ( rule__ZombieSpecifics__Group_3__0 )?
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup_3()); 
            // InternalMazeDsl.g:2623:2: ( rule__ZombieSpecifics__Group_3__0 )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==52) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalMazeDsl.g:2623:3: rule__ZombieSpecifics__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ZombieSpecifics__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getZombieSpecificsAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__3__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__4"
    // InternalMazeDsl.g:2631:1: rule__ZombieSpecifics__Group__4 : rule__ZombieSpecifics__Group__4__Impl rule__ZombieSpecifics__Group__5 ;
    public final void rule__ZombieSpecifics__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2635:1: ( rule__ZombieSpecifics__Group__4__Impl rule__ZombieSpecifics__Group__5 )
            // InternalMazeDsl.g:2636:2: rule__ZombieSpecifics__Group__4__Impl rule__ZombieSpecifics__Group__5
            {
            pushFollow(FOLLOW_24);
            rule__ZombieSpecifics__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__4"


    // $ANTLR start "rule__ZombieSpecifics__Group__4__Impl"
    // InternalMazeDsl.g:2643:1: rule__ZombieSpecifics__Group__4__Impl : ( ( rule__ZombieSpecifics__Group_4__0 )? ) ;
    public final void rule__ZombieSpecifics__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2647:1: ( ( ( rule__ZombieSpecifics__Group_4__0 )? ) )
            // InternalMazeDsl.g:2648:1: ( ( rule__ZombieSpecifics__Group_4__0 )? )
            {
            // InternalMazeDsl.g:2648:1: ( ( rule__ZombieSpecifics__Group_4__0 )? )
            // InternalMazeDsl.g:2649:2: ( rule__ZombieSpecifics__Group_4__0 )?
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup_4()); 
            // InternalMazeDsl.g:2650:2: ( rule__ZombieSpecifics__Group_4__0 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==53) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalMazeDsl.g:2650:3: rule__ZombieSpecifics__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ZombieSpecifics__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getZombieSpecificsAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__4__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__5"
    // InternalMazeDsl.g:2658:1: rule__ZombieSpecifics__Group__5 : rule__ZombieSpecifics__Group__5__Impl rule__ZombieSpecifics__Group__6 ;
    public final void rule__ZombieSpecifics__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2662:1: ( rule__ZombieSpecifics__Group__5__Impl rule__ZombieSpecifics__Group__6 )
            // InternalMazeDsl.g:2663:2: rule__ZombieSpecifics__Group__5__Impl rule__ZombieSpecifics__Group__6
            {
            pushFollow(FOLLOW_24);
            rule__ZombieSpecifics__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__5"


    // $ANTLR start "rule__ZombieSpecifics__Group__5__Impl"
    // InternalMazeDsl.g:2670:1: rule__ZombieSpecifics__Group__5__Impl : ( ( rule__ZombieSpecifics__Group_5__0 )? ) ;
    public final void rule__ZombieSpecifics__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2674:1: ( ( ( rule__ZombieSpecifics__Group_5__0 )? ) )
            // InternalMazeDsl.g:2675:1: ( ( rule__ZombieSpecifics__Group_5__0 )? )
            {
            // InternalMazeDsl.g:2675:1: ( ( rule__ZombieSpecifics__Group_5__0 )? )
            // InternalMazeDsl.g:2676:2: ( rule__ZombieSpecifics__Group_5__0 )?
            {
             before(grammarAccess.getZombieSpecificsAccess().getGroup_5()); 
            // InternalMazeDsl.g:2677:2: ( rule__ZombieSpecifics__Group_5__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==54) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalMazeDsl.g:2677:3: rule__ZombieSpecifics__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ZombieSpecifics__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getZombieSpecificsAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__5__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group__6"
    // InternalMazeDsl.g:2685:1: rule__ZombieSpecifics__Group__6 : rule__ZombieSpecifics__Group__6__Impl ;
    public final void rule__ZombieSpecifics__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2689:1: ( rule__ZombieSpecifics__Group__6__Impl )
            // InternalMazeDsl.g:2690:2: rule__ZombieSpecifics__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group__6__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__6"


    // $ANTLR start "rule__ZombieSpecifics__Group__6__Impl"
    // InternalMazeDsl.g:2696:1: rule__ZombieSpecifics__Group__6__Impl : ( '}' ) ;
    public final void rule__ZombieSpecifics__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2700:1: ( ( '}' ) )
            // InternalMazeDsl.g:2701:1: ( '}' )
            {
            // InternalMazeDsl.g:2701:1: ( '}' )
            // InternalMazeDsl.g:2702:2: '}'
            {
             before(grammarAccess.getZombieSpecificsAccess().getRightCurlyBracketKeyword_6()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getRightCurlyBracketKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group__6__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group_3__0"
    // InternalMazeDsl.g:2712:1: rule__ZombieSpecifics__Group_3__0 : rule__ZombieSpecifics__Group_3__0__Impl rule__ZombieSpecifics__Group_3__1 ;
    public final void rule__ZombieSpecifics__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2716:1: ( rule__ZombieSpecifics__Group_3__0__Impl rule__ZombieSpecifics__Group_3__1 )
            // InternalMazeDsl.g:2717:2: rule__ZombieSpecifics__Group_3__0__Impl rule__ZombieSpecifics__Group_3__1
            {
            pushFollow(FOLLOW_25);
            rule__ZombieSpecifics__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_3__0"


    // $ANTLR start "rule__ZombieSpecifics__Group_3__0__Impl"
    // InternalMazeDsl.g:2724:1: rule__ZombieSpecifics__Group_3__0__Impl : ( 'attackDamage' ) ;
    public final void rule__ZombieSpecifics__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2728:1: ( ( 'attackDamage' ) )
            // InternalMazeDsl.g:2729:1: ( 'attackDamage' )
            {
            // InternalMazeDsl.g:2729:1: ( 'attackDamage' )
            // InternalMazeDsl.g:2730:2: 'attackDamage'
            {
             before(grammarAccess.getZombieSpecificsAccess().getAttackDamageKeyword_3_0()); 
            match(input,52,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getAttackDamageKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_3__0__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group_3__1"
    // InternalMazeDsl.g:2739:1: rule__ZombieSpecifics__Group_3__1 : rule__ZombieSpecifics__Group_3__1__Impl ;
    public final void rule__ZombieSpecifics__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2743:1: ( rule__ZombieSpecifics__Group_3__1__Impl )
            // InternalMazeDsl.g:2744:2: rule__ZombieSpecifics__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_3__1"


    // $ANTLR start "rule__ZombieSpecifics__Group_3__1__Impl"
    // InternalMazeDsl.g:2750:1: rule__ZombieSpecifics__Group_3__1__Impl : ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) ) ;
    public final void rule__ZombieSpecifics__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2754:1: ( ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) ) )
            // InternalMazeDsl.g:2755:1: ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:2755:1: ( ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 ) )
            // InternalMazeDsl.g:2756:2: ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getAttackDamageAssignment_3_1()); 
            // InternalMazeDsl.g:2757:2: ( rule__ZombieSpecifics__AttackDamageAssignment_3_1 )
            // InternalMazeDsl.g:2757:3: rule__ZombieSpecifics__AttackDamageAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__AttackDamageAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getZombieSpecificsAccess().getAttackDamageAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_3__1__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group_4__0"
    // InternalMazeDsl.g:2766:1: rule__ZombieSpecifics__Group_4__0 : rule__ZombieSpecifics__Group_4__0__Impl rule__ZombieSpecifics__Group_4__1 ;
    public final void rule__ZombieSpecifics__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2770:1: ( rule__ZombieSpecifics__Group_4__0__Impl rule__ZombieSpecifics__Group_4__1 )
            // InternalMazeDsl.g:2771:2: rule__ZombieSpecifics__Group_4__0__Impl rule__ZombieSpecifics__Group_4__1
            {
            pushFollow(FOLLOW_25);
            rule__ZombieSpecifics__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_4__0"


    // $ANTLR start "rule__ZombieSpecifics__Group_4__0__Impl"
    // InternalMazeDsl.g:2778:1: rule__ZombieSpecifics__Group_4__0__Impl : ( 'infectionLevel' ) ;
    public final void rule__ZombieSpecifics__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2782:1: ( ( 'infectionLevel' ) )
            // InternalMazeDsl.g:2783:1: ( 'infectionLevel' )
            {
            // InternalMazeDsl.g:2783:1: ( 'infectionLevel' )
            // InternalMazeDsl.g:2784:2: 'infectionLevel'
            {
             before(grammarAccess.getZombieSpecificsAccess().getInfectionLevelKeyword_4_0()); 
            match(input,53,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getInfectionLevelKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_4__0__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group_4__1"
    // InternalMazeDsl.g:2793:1: rule__ZombieSpecifics__Group_4__1 : rule__ZombieSpecifics__Group_4__1__Impl ;
    public final void rule__ZombieSpecifics__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2797:1: ( rule__ZombieSpecifics__Group_4__1__Impl )
            // InternalMazeDsl.g:2798:2: rule__ZombieSpecifics__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_4__1"


    // $ANTLR start "rule__ZombieSpecifics__Group_4__1__Impl"
    // InternalMazeDsl.g:2804:1: rule__ZombieSpecifics__Group_4__1__Impl : ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) ) ;
    public final void rule__ZombieSpecifics__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2808:1: ( ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) ) )
            // InternalMazeDsl.g:2809:1: ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:2809:1: ( ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 ) )
            // InternalMazeDsl.g:2810:2: ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getInfectionLevelAssignment_4_1()); 
            // InternalMazeDsl.g:2811:2: ( rule__ZombieSpecifics__InfectionLevelAssignment_4_1 )
            // InternalMazeDsl.g:2811:3: rule__ZombieSpecifics__InfectionLevelAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__InfectionLevelAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getZombieSpecificsAccess().getInfectionLevelAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_4__1__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group_5__0"
    // InternalMazeDsl.g:2820:1: rule__ZombieSpecifics__Group_5__0 : rule__ZombieSpecifics__Group_5__0__Impl rule__ZombieSpecifics__Group_5__1 ;
    public final void rule__ZombieSpecifics__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2824:1: ( rule__ZombieSpecifics__Group_5__0__Impl rule__ZombieSpecifics__Group_5__1 )
            // InternalMazeDsl.g:2825:2: rule__ZombieSpecifics__Group_5__0__Impl rule__ZombieSpecifics__Group_5__1
            {
            pushFollow(FOLLOW_25);
            rule__ZombieSpecifics__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_5__0"


    // $ANTLR start "rule__ZombieSpecifics__Group_5__0__Impl"
    // InternalMazeDsl.g:2832:1: rule__ZombieSpecifics__Group_5__0__Impl : ( 'resurrectionTime' ) ;
    public final void rule__ZombieSpecifics__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2836:1: ( ( 'resurrectionTime' ) )
            // InternalMazeDsl.g:2837:1: ( 'resurrectionTime' )
            {
            // InternalMazeDsl.g:2837:1: ( 'resurrectionTime' )
            // InternalMazeDsl.g:2838:2: 'resurrectionTime'
            {
             before(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeKeyword_5_0()); 
            match(input,54,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_5__0__Impl"


    // $ANTLR start "rule__ZombieSpecifics__Group_5__1"
    // InternalMazeDsl.g:2847:1: rule__ZombieSpecifics__Group_5__1 : rule__ZombieSpecifics__Group_5__1__Impl ;
    public final void rule__ZombieSpecifics__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2851:1: ( rule__ZombieSpecifics__Group_5__1__Impl )
            // InternalMazeDsl.g:2852:2: rule__ZombieSpecifics__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_5__1"


    // $ANTLR start "rule__ZombieSpecifics__Group_5__1__Impl"
    // InternalMazeDsl.g:2858:1: rule__ZombieSpecifics__Group_5__1__Impl : ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) ) ;
    public final void rule__ZombieSpecifics__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2862:1: ( ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) ) )
            // InternalMazeDsl.g:2863:1: ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:2863:1: ( ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 ) )
            // InternalMazeDsl.g:2864:2: ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 )
            {
             before(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeAssignment_5_1()); 
            // InternalMazeDsl.g:2865:2: ( rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 )
            // InternalMazeDsl.g:2865:3: rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__Group_5__1__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__0"
    // InternalMazeDsl.g:2874:1: rule__GhostSpecifics__Group__0 : rule__GhostSpecifics__Group__0__Impl rule__GhostSpecifics__Group__1 ;
    public final void rule__GhostSpecifics__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2878:1: ( rule__GhostSpecifics__Group__0__Impl rule__GhostSpecifics__Group__1 )
            // InternalMazeDsl.g:2879:2: rule__GhostSpecifics__Group__0__Impl rule__GhostSpecifics__Group__1
            {
            pushFollow(FOLLOW_26);
            rule__GhostSpecifics__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__0"


    // $ANTLR start "rule__GhostSpecifics__Group__0__Impl"
    // InternalMazeDsl.g:2886:1: rule__GhostSpecifics__Group__0__Impl : ( () ) ;
    public final void rule__GhostSpecifics__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2890:1: ( ( () ) )
            // InternalMazeDsl.g:2891:1: ( () )
            {
            // InternalMazeDsl.g:2891:1: ( () )
            // InternalMazeDsl.g:2892:2: ()
            {
             before(grammarAccess.getGhostSpecificsAccess().getGhostSpecificsAction_0()); 
            // InternalMazeDsl.g:2893:2: ()
            // InternalMazeDsl.g:2893:3: 
            {
            }

             after(grammarAccess.getGhostSpecificsAccess().getGhostSpecificsAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__0__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__1"
    // InternalMazeDsl.g:2901:1: rule__GhostSpecifics__Group__1 : rule__GhostSpecifics__Group__1__Impl rule__GhostSpecifics__Group__2 ;
    public final void rule__GhostSpecifics__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2905:1: ( rule__GhostSpecifics__Group__1__Impl rule__GhostSpecifics__Group__2 )
            // InternalMazeDsl.g:2906:2: rule__GhostSpecifics__Group__1__Impl rule__GhostSpecifics__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__GhostSpecifics__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__1"


    // $ANTLR start "rule__GhostSpecifics__Group__1__Impl"
    // InternalMazeDsl.g:2913:1: rule__GhostSpecifics__Group__1__Impl : ( 'ghost-stats' ) ;
    public final void rule__GhostSpecifics__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2917:1: ( ( 'ghost-stats' ) )
            // InternalMazeDsl.g:2918:1: ( 'ghost-stats' )
            {
            // InternalMazeDsl.g:2918:1: ( 'ghost-stats' )
            // InternalMazeDsl.g:2919:2: 'ghost-stats'
            {
             before(grammarAccess.getGhostSpecificsAccess().getGhostStatsKeyword_1()); 
            match(input,55,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getGhostStatsKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__1__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__2"
    // InternalMazeDsl.g:2928:1: rule__GhostSpecifics__Group__2 : rule__GhostSpecifics__Group__2__Impl rule__GhostSpecifics__Group__3 ;
    public final void rule__GhostSpecifics__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2932:1: ( rule__GhostSpecifics__Group__2__Impl rule__GhostSpecifics__Group__3 )
            // InternalMazeDsl.g:2933:2: rule__GhostSpecifics__Group__2__Impl rule__GhostSpecifics__Group__3
            {
            pushFollow(FOLLOW_27);
            rule__GhostSpecifics__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__2"


    // $ANTLR start "rule__GhostSpecifics__Group__2__Impl"
    // InternalMazeDsl.g:2940:1: rule__GhostSpecifics__Group__2__Impl : ( '{' ) ;
    public final void rule__GhostSpecifics__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2944:1: ( ( '{' ) )
            // InternalMazeDsl.g:2945:1: ( '{' )
            {
            // InternalMazeDsl.g:2945:1: ( '{' )
            // InternalMazeDsl.g:2946:2: '{'
            {
             before(grammarAccess.getGhostSpecificsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__2__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__3"
    // InternalMazeDsl.g:2955:1: rule__GhostSpecifics__Group__3 : rule__GhostSpecifics__Group__3__Impl rule__GhostSpecifics__Group__4 ;
    public final void rule__GhostSpecifics__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2959:1: ( rule__GhostSpecifics__Group__3__Impl rule__GhostSpecifics__Group__4 )
            // InternalMazeDsl.g:2960:2: rule__GhostSpecifics__Group__3__Impl rule__GhostSpecifics__Group__4
            {
            pushFollow(FOLLOW_27);
            rule__GhostSpecifics__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__3"


    // $ANTLR start "rule__GhostSpecifics__Group__3__Impl"
    // InternalMazeDsl.g:2967:1: rule__GhostSpecifics__Group__3__Impl : ( ( rule__GhostSpecifics__Group_3__0 )? ) ;
    public final void rule__GhostSpecifics__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2971:1: ( ( ( rule__GhostSpecifics__Group_3__0 )? ) )
            // InternalMazeDsl.g:2972:1: ( ( rule__GhostSpecifics__Group_3__0 )? )
            {
            // InternalMazeDsl.g:2972:1: ( ( rule__GhostSpecifics__Group_3__0 )? )
            // InternalMazeDsl.g:2973:2: ( rule__GhostSpecifics__Group_3__0 )?
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup_3()); 
            // InternalMazeDsl.g:2974:2: ( rule__GhostSpecifics__Group_3__0 )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==52) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalMazeDsl.g:2974:3: rule__GhostSpecifics__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__GhostSpecifics__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGhostSpecificsAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__3__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__4"
    // InternalMazeDsl.g:2982:1: rule__GhostSpecifics__Group__4 : rule__GhostSpecifics__Group__4__Impl rule__GhostSpecifics__Group__5 ;
    public final void rule__GhostSpecifics__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2986:1: ( rule__GhostSpecifics__Group__4__Impl rule__GhostSpecifics__Group__5 )
            // InternalMazeDsl.g:2987:2: rule__GhostSpecifics__Group__4__Impl rule__GhostSpecifics__Group__5
            {
            pushFollow(FOLLOW_27);
            rule__GhostSpecifics__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__4"


    // $ANTLR start "rule__GhostSpecifics__Group__4__Impl"
    // InternalMazeDsl.g:2994:1: rule__GhostSpecifics__Group__4__Impl : ( ( rule__GhostSpecifics__Group_4__0 )? ) ;
    public final void rule__GhostSpecifics__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:2998:1: ( ( ( rule__GhostSpecifics__Group_4__0 )? ) )
            // InternalMazeDsl.g:2999:1: ( ( rule__GhostSpecifics__Group_4__0 )? )
            {
            // InternalMazeDsl.g:2999:1: ( ( rule__GhostSpecifics__Group_4__0 )? )
            // InternalMazeDsl.g:3000:2: ( rule__GhostSpecifics__Group_4__0 )?
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup_4()); 
            // InternalMazeDsl.g:3001:2: ( rule__GhostSpecifics__Group_4__0 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==56) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalMazeDsl.g:3001:3: rule__GhostSpecifics__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__GhostSpecifics__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGhostSpecificsAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__4__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__5"
    // InternalMazeDsl.g:3009:1: rule__GhostSpecifics__Group__5 : rule__GhostSpecifics__Group__5__Impl rule__GhostSpecifics__Group__6 ;
    public final void rule__GhostSpecifics__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3013:1: ( rule__GhostSpecifics__Group__5__Impl rule__GhostSpecifics__Group__6 )
            // InternalMazeDsl.g:3014:2: rule__GhostSpecifics__Group__5__Impl rule__GhostSpecifics__Group__6
            {
            pushFollow(FOLLOW_27);
            rule__GhostSpecifics__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__5"


    // $ANTLR start "rule__GhostSpecifics__Group__5__Impl"
    // InternalMazeDsl.g:3021:1: rule__GhostSpecifics__Group__5__Impl : ( ( rule__GhostSpecifics__Group_5__0 )? ) ;
    public final void rule__GhostSpecifics__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3025:1: ( ( ( rule__GhostSpecifics__Group_5__0 )? ) )
            // InternalMazeDsl.g:3026:1: ( ( rule__GhostSpecifics__Group_5__0 )? )
            {
            // InternalMazeDsl.g:3026:1: ( ( rule__GhostSpecifics__Group_5__0 )? )
            // InternalMazeDsl.g:3027:2: ( rule__GhostSpecifics__Group_5__0 )?
            {
             before(grammarAccess.getGhostSpecificsAccess().getGroup_5()); 
            // InternalMazeDsl.g:3028:2: ( rule__GhostSpecifics__Group_5__0 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==57) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalMazeDsl.g:3028:3: rule__GhostSpecifics__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__GhostSpecifics__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getGhostSpecificsAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__5__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group__6"
    // InternalMazeDsl.g:3036:1: rule__GhostSpecifics__Group__6 : rule__GhostSpecifics__Group__6__Impl ;
    public final void rule__GhostSpecifics__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3040:1: ( rule__GhostSpecifics__Group__6__Impl )
            // InternalMazeDsl.g:3041:2: rule__GhostSpecifics__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group__6__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__6"


    // $ANTLR start "rule__GhostSpecifics__Group__6__Impl"
    // InternalMazeDsl.g:3047:1: rule__GhostSpecifics__Group__6__Impl : ( '}' ) ;
    public final void rule__GhostSpecifics__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3051:1: ( ( '}' ) )
            // InternalMazeDsl.g:3052:1: ( '}' )
            {
            // InternalMazeDsl.g:3052:1: ( '}' )
            // InternalMazeDsl.g:3053:2: '}'
            {
             before(grammarAccess.getGhostSpecificsAccess().getRightCurlyBracketKeyword_6()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getRightCurlyBracketKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group__6__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group_3__0"
    // InternalMazeDsl.g:3063:1: rule__GhostSpecifics__Group_3__0 : rule__GhostSpecifics__Group_3__0__Impl rule__GhostSpecifics__Group_3__1 ;
    public final void rule__GhostSpecifics__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3067:1: ( rule__GhostSpecifics__Group_3__0__Impl rule__GhostSpecifics__Group_3__1 )
            // InternalMazeDsl.g:3068:2: rule__GhostSpecifics__Group_3__0__Impl rule__GhostSpecifics__Group_3__1
            {
            pushFollow(FOLLOW_25);
            rule__GhostSpecifics__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_3__0"


    // $ANTLR start "rule__GhostSpecifics__Group_3__0__Impl"
    // InternalMazeDsl.g:3075:1: rule__GhostSpecifics__Group_3__0__Impl : ( 'attackDamage' ) ;
    public final void rule__GhostSpecifics__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3079:1: ( ( 'attackDamage' ) )
            // InternalMazeDsl.g:3080:1: ( 'attackDamage' )
            {
            // InternalMazeDsl.g:3080:1: ( 'attackDamage' )
            // InternalMazeDsl.g:3081:2: 'attackDamage'
            {
             before(grammarAccess.getGhostSpecificsAccess().getAttackDamageKeyword_3_0()); 
            match(input,52,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getAttackDamageKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_3__0__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group_3__1"
    // InternalMazeDsl.g:3090:1: rule__GhostSpecifics__Group_3__1 : rule__GhostSpecifics__Group_3__1__Impl ;
    public final void rule__GhostSpecifics__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3094:1: ( rule__GhostSpecifics__Group_3__1__Impl )
            // InternalMazeDsl.g:3095:2: rule__GhostSpecifics__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_3__1"


    // $ANTLR start "rule__GhostSpecifics__Group_3__1__Impl"
    // InternalMazeDsl.g:3101:1: rule__GhostSpecifics__Group_3__1__Impl : ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) ) ;
    public final void rule__GhostSpecifics__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3105:1: ( ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) ) )
            // InternalMazeDsl.g:3106:1: ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:3106:1: ( ( rule__GhostSpecifics__AttackDamageAssignment_3_1 ) )
            // InternalMazeDsl.g:3107:2: ( rule__GhostSpecifics__AttackDamageAssignment_3_1 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getAttackDamageAssignment_3_1()); 
            // InternalMazeDsl.g:3108:2: ( rule__GhostSpecifics__AttackDamageAssignment_3_1 )
            // InternalMazeDsl.g:3108:3: rule__GhostSpecifics__AttackDamageAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__AttackDamageAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getGhostSpecificsAccess().getAttackDamageAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_3__1__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group_4__0"
    // InternalMazeDsl.g:3117:1: rule__GhostSpecifics__Group_4__0 : rule__GhostSpecifics__Group_4__0__Impl rule__GhostSpecifics__Group_4__1 ;
    public final void rule__GhostSpecifics__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3121:1: ( rule__GhostSpecifics__Group_4__0__Impl rule__GhostSpecifics__Group_4__1 )
            // InternalMazeDsl.g:3122:2: rule__GhostSpecifics__Group_4__0__Impl rule__GhostSpecifics__Group_4__1
            {
            pushFollow(FOLLOW_25);
            rule__GhostSpecifics__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_4__0"


    // $ANTLR start "rule__GhostSpecifics__Group_4__0__Impl"
    // InternalMazeDsl.g:3129:1: rule__GhostSpecifics__Group_4__0__Impl : ( 'visibilityLevel' ) ;
    public final void rule__GhostSpecifics__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3133:1: ( ( 'visibilityLevel' ) )
            // InternalMazeDsl.g:3134:1: ( 'visibilityLevel' )
            {
            // InternalMazeDsl.g:3134:1: ( 'visibilityLevel' )
            // InternalMazeDsl.g:3135:2: 'visibilityLevel'
            {
             before(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelKeyword_4_0()); 
            match(input,56,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_4__0__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group_4__1"
    // InternalMazeDsl.g:3144:1: rule__GhostSpecifics__Group_4__1 : rule__GhostSpecifics__Group_4__1__Impl ;
    public final void rule__GhostSpecifics__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3148:1: ( rule__GhostSpecifics__Group_4__1__Impl )
            // InternalMazeDsl.g:3149:2: rule__GhostSpecifics__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_4__1"


    // $ANTLR start "rule__GhostSpecifics__Group_4__1__Impl"
    // InternalMazeDsl.g:3155:1: rule__GhostSpecifics__Group_4__1__Impl : ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) ) ;
    public final void rule__GhostSpecifics__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3159:1: ( ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) ) )
            // InternalMazeDsl.g:3160:1: ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:3160:1: ( ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 ) )
            // InternalMazeDsl.g:3161:2: ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelAssignment_4_1()); 
            // InternalMazeDsl.g:3162:2: ( rule__GhostSpecifics__VisibilityLevelAssignment_4_1 )
            // InternalMazeDsl.g:3162:3: rule__GhostSpecifics__VisibilityLevelAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__VisibilityLevelAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_4__1__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group_5__0"
    // InternalMazeDsl.g:3171:1: rule__GhostSpecifics__Group_5__0 : rule__GhostSpecifics__Group_5__0__Impl rule__GhostSpecifics__Group_5__1 ;
    public final void rule__GhostSpecifics__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3175:1: ( rule__GhostSpecifics__Group_5__0__Impl rule__GhostSpecifics__Group_5__1 )
            // InternalMazeDsl.g:3176:2: rule__GhostSpecifics__Group_5__0__Impl rule__GhostSpecifics__Group_5__1
            {
            pushFollow(FOLLOW_16);
            rule__GhostSpecifics__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_5__0"


    // $ANTLR start "rule__GhostSpecifics__Group_5__0__Impl"
    // InternalMazeDsl.g:3183:1: rule__GhostSpecifics__Group_5__0__Impl : ( 'nonTangibilityEnergy' ) ;
    public final void rule__GhostSpecifics__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3187:1: ( ( 'nonTangibilityEnergy' ) )
            // InternalMazeDsl.g:3188:1: ( 'nonTangibilityEnergy' )
            {
            // InternalMazeDsl.g:3188:1: ( 'nonTangibilityEnergy' )
            // InternalMazeDsl.g:3189:2: 'nonTangibilityEnergy'
            {
             before(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyKeyword_5_0()); 
            match(input,57,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_5__0__Impl"


    // $ANTLR start "rule__GhostSpecifics__Group_5__1"
    // InternalMazeDsl.g:3198:1: rule__GhostSpecifics__Group_5__1 : rule__GhostSpecifics__Group_5__1__Impl ;
    public final void rule__GhostSpecifics__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3202:1: ( rule__GhostSpecifics__Group_5__1__Impl )
            // InternalMazeDsl.g:3203:2: rule__GhostSpecifics__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_5__1"


    // $ANTLR start "rule__GhostSpecifics__Group_5__1__Impl"
    // InternalMazeDsl.g:3209:1: rule__GhostSpecifics__Group_5__1__Impl : ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) ) ;
    public final void rule__GhostSpecifics__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3213:1: ( ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) ) )
            // InternalMazeDsl.g:3214:1: ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:3214:1: ( ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 ) )
            // InternalMazeDsl.g:3215:2: ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 )
            {
             before(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyAssignment_5_1()); 
            // InternalMazeDsl.g:3216:2: ( rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 )
            // InternalMazeDsl.g:3216:3: rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__Group_5__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__0"
    // InternalMazeDsl.g:3225:1: rule__RangedSpecifics__Group__0 : rule__RangedSpecifics__Group__0__Impl rule__RangedSpecifics__Group__1 ;
    public final void rule__RangedSpecifics__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3229:1: ( rule__RangedSpecifics__Group__0__Impl rule__RangedSpecifics__Group__1 )
            // InternalMazeDsl.g:3230:2: rule__RangedSpecifics__Group__0__Impl rule__RangedSpecifics__Group__1
            {
            pushFollow(FOLLOW_28);
            rule__RangedSpecifics__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__0"


    // $ANTLR start "rule__RangedSpecifics__Group__0__Impl"
    // InternalMazeDsl.g:3237:1: rule__RangedSpecifics__Group__0__Impl : ( () ) ;
    public final void rule__RangedSpecifics__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3241:1: ( ( () ) )
            // InternalMazeDsl.g:3242:1: ( () )
            {
            // InternalMazeDsl.g:3242:1: ( () )
            // InternalMazeDsl.g:3243:2: ()
            {
             before(grammarAccess.getRangedSpecificsAccess().getRangedSpecificsAction_0()); 
            // InternalMazeDsl.g:3244:2: ()
            // InternalMazeDsl.g:3244:3: 
            {
            }

             after(grammarAccess.getRangedSpecificsAccess().getRangedSpecificsAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__1"
    // InternalMazeDsl.g:3252:1: rule__RangedSpecifics__Group__1 : rule__RangedSpecifics__Group__1__Impl rule__RangedSpecifics__Group__2 ;
    public final void rule__RangedSpecifics__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3256:1: ( rule__RangedSpecifics__Group__1__Impl rule__RangedSpecifics__Group__2 )
            // InternalMazeDsl.g:3257:2: rule__RangedSpecifics__Group__1__Impl rule__RangedSpecifics__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__RangedSpecifics__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__1"


    // $ANTLR start "rule__RangedSpecifics__Group__1__Impl"
    // InternalMazeDsl.g:3264:1: rule__RangedSpecifics__Group__1__Impl : ( 'ranged-stats' ) ;
    public final void rule__RangedSpecifics__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3268:1: ( ( 'ranged-stats' ) )
            // InternalMazeDsl.g:3269:1: ( 'ranged-stats' )
            {
            // InternalMazeDsl.g:3269:1: ( 'ranged-stats' )
            // InternalMazeDsl.g:3270:2: 'ranged-stats'
            {
             before(grammarAccess.getRangedSpecificsAccess().getRangedStatsKeyword_1()); 
            match(input,58,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getRangedStatsKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__2"
    // InternalMazeDsl.g:3279:1: rule__RangedSpecifics__Group__2 : rule__RangedSpecifics__Group__2__Impl rule__RangedSpecifics__Group__3 ;
    public final void rule__RangedSpecifics__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3283:1: ( rule__RangedSpecifics__Group__2__Impl rule__RangedSpecifics__Group__3 )
            // InternalMazeDsl.g:3284:2: rule__RangedSpecifics__Group__2__Impl rule__RangedSpecifics__Group__3
            {
            pushFollow(FOLLOW_29);
            rule__RangedSpecifics__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__2"


    // $ANTLR start "rule__RangedSpecifics__Group__2__Impl"
    // InternalMazeDsl.g:3291:1: rule__RangedSpecifics__Group__2__Impl : ( '{' ) ;
    public final void rule__RangedSpecifics__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3295:1: ( ( '{' ) )
            // InternalMazeDsl.g:3296:1: ( '{' )
            {
            // InternalMazeDsl.g:3296:1: ( '{' )
            // InternalMazeDsl.g:3297:2: '{'
            {
             before(grammarAccess.getRangedSpecificsAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__2__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__3"
    // InternalMazeDsl.g:3306:1: rule__RangedSpecifics__Group__3 : rule__RangedSpecifics__Group__3__Impl rule__RangedSpecifics__Group__4 ;
    public final void rule__RangedSpecifics__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3310:1: ( rule__RangedSpecifics__Group__3__Impl rule__RangedSpecifics__Group__4 )
            // InternalMazeDsl.g:3311:2: rule__RangedSpecifics__Group__3__Impl rule__RangedSpecifics__Group__4
            {
            pushFollow(FOLLOW_29);
            rule__RangedSpecifics__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__3"


    // $ANTLR start "rule__RangedSpecifics__Group__3__Impl"
    // InternalMazeDsl.g:3318:1: rule__RangedSpecifics__Group__3__Impl : ( ( rule__RangedSpecifics__Group_3__0 )? ) ;
    public final void rule__RangedSpecifics__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3322:1: ( ( ( rule__RangedSpecifics__Group_3__0 )? ) )
            // InternalMazeDsl.g:3323:1: ( ( rule__RangedSpecifics__Group_3__0 )? )
            {
            // InternalMazeDsl.g:3323:1: ( ( rule__RangedSpecifics__Group_3__0 )? )
            // InternalMazeDsl.g:3324:2: ( rule__RangedSpecifics__Group_3__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_3()); 
            // InternalMazeDsl.g:3325:2: ( rule__RangedSpecifics__Group_3__0 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==59) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalMazeDsl.g:3325:3: rule__RangedSpecifics__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RangedSpecifics__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__3__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__4"
    // InternalMazeDsl.g:3333:1: rule__RangedSpecifics__Group__4 : rule__RangedSpecifics__Group__4__Impl rule__RangedSpecifics__Group__5 ;
    public final void rule__RangedSpecifics__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3337:1: ( rule__RangedSpecifics__Group__4__Impl rule__RangedSpecifics__Group__5 )
            // InternalMazeDsl.g:3338:2: rule__RangedSpecifics__Group__4__Impl rule__RangedSpecifics__Group__5
            {
            pushFollow(FOLLOW_29);
            rule__RangedSpecifics__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__4"


    // $ANTLR start "rule__RangedSpecifics__Group__4__Impl"
    // InternalMazeDsl.g:3345:1: rule__RangedSpecifics__Group__4__Impl : ( ( rule__RangedSpecifics__Group_4__0 )? ) ;
    public final void rule__RangedSpecifics__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3349:1: ( ( ( rule__RangedSpecifics__Group_4__0 )? ) )
            // InternalMazeDsl.g:3350:1: ( ( rule__RangedSpecifics__Group_4__0 )? )
            {
            // InternalMazeDsl.g:3350:1: ( ( rule__RangedSpecifics__Group_4__0 )? )
            // InternalMazeDsl.g:3351:2: ( rule__RangedSpecifics__Group_4__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_4()); 
            // InternalMazeDsl.g:3352:2: ( rule__RangedSpecifics__Group_4__0 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==60) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalMazeDsl.g:3352:3: rule__RangedSpecifics__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RangedSpecifics__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__4__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__5"
    // InternalMazeDsl.g:3360:1: rule__RangedSpecifics__Group__5 : rule__RangedSpecifics__Group__5__Impl rule__RangedSpecifics__Group__6 ;
    public final void rule__RangedSpecifics__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3364:1: ( rule__RangedSpecifics__Group__5__Impl rule__RangedSpecifics__Group__6 )
            // InternalMazeDsl.g:3365:2: rule__RangedSpecifics__Group__5__Impl rule__RangedSpecifics__Group__6
            {
            pushFollow(FOLLOW_29);
            rule__RangedSpecifics__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__5"


    // $ANTLR start "rule__RangedSpecifics__Group__5__Impl"
    // InternalMazeDsl.g:3372:1: rule__RangedSpecifics__Group__5__Impl : ( ( rule__RangedSpecifics__Group_5__0 )? ) ;
    public final void rule__RangedSpecifics__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3376:1: ( ( ( rule__RangedSpecifics__Group_5__0 )? ) )
            // InternalMazeDsl.g:3377:1: ( ( rule__RangedSpecifics__Group_5__0 )? )
            {
            // InternalMazeDsl.g:3377:1: ( ( rule__RangedSpecifics__Group_5__0 )? )
            // InternalMazeDsl.g:3378:2: ( rule__RangedSpecifics__Group_5__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_5()); 
            // InternalMazeDsl.g:3379:2: ( rule__RangedSpecifics__Group_5__0 )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==52) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // InternalMazeDsl.g:3379:3: rule__RangedSpecifics__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RangedSpecifics__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__5__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__6"
    // InternalMazeDsl.g:3387:1: rule__RangedSpecifics__Group__6 : rule__RangedSpecifics__Group__6__Impl rule__RangedSpecifics__Group__7 ;
    public final void rule__RangedSpecifics__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3391:1: ( rule__RangedSpecifics__Group__6__Impl rule__RangedSpecifics__Group__7 )
            // InternalMazeDsl.g:3392:2: rule__RangedSpecifics__Group__6__Impl rule__RangedSpecifics__Group__7
            {
            pushFollow(FOLLOW_29);
            rule__RangedSpecifics__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__6"


    // $ANTLR start "rule__RangedSpecifics__Group__6__Impl"
    // InternalMazeDsl.g:3399:1: rule__RangedSpecifics__Group__6__Impl : ( ( rule__RangedSpecifics__Group_6__0 )? ) ;
    public final void rule__RangedSpecifics__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3403:1: ( ( ( rule__RangedSpecifics__Group_6__0 )? ) )
            // InternalMazeDsl.g:3404:1: ( ( rule__RangedSpecifics__Group_6__0 )? )
            {
            // InternalMazeDsl.g:3404:1: ( ( rule__RangedSpecifics__Group_6__0 )? )
            // InternalMazeDsl.g:3405:2: ( rule__RangedSpecifics__Group_6__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_6()); 
            // InternalMazeDsl.g:3406:2: ( rule__RangedSpecifics__Group_6__0 )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==61) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalMazeDsl.g:3406:3: rule__RangedSpecifics__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RangedSpecifics__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__6__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__7"
    // InternalMazeDsl.g:3414:1: rule__RangedSpecifics__Group__7 : rule__RangedSpecifics__Group__7__Impl rule__RangedSpecifics__Group__8 ;
    public final void rule__RangedSpecifics__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3418:1: ( rule__RangedSpecifics__Group__7__Impl rule__RangedSpecifics__Group__8 )
            // InternalMazeDsl.g:3419:2: rule__RangedSpecifics__Group__7__Impl rule__RangedSpecifics__Group__8
            {
            pushFollow(FOLLOW_29);
            rule__RangedSpecifics__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__7"


    // $ANTLR start "rule__RangedSpecifics__Group__7__Impl"
    // InternalMazeDsl.g:3426:1: rule__RangedSpecifics__Group__7__Impl : ( ( rule__RangedSpecifics__Group_7__0 )? ) ;
    public final void rule__RangedSpecifics__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3430:1: ( ( ( rule__RangedSpecifics__Group_7__0 )? ) )
            // InternalMazeDsl.g:3431:1: ( ( rule__RangedSpecifics__Group_7__0 )? )
            {
            // InternalMazeDsl.g:3431:1: ( ( rule__RangedSpecifics__Group_7__0 )? )
            // InternalMazeDsl.g:3432:2: ( rule__RangedSpecifics__Group_7__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_7()); 
            // InternalMazeDsl.g:3433:2: ( rule__RangedSpecifics__Group_7__0 )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==62) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalMazeDsl.g:3433:3: rule__RangedSpecifics__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RangedSpecifics__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__7__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__8"
    // InternalMazeDsl.g:3441:1: rule__RangedSpecifics__Group__8 : rule__RangedSpecifics__Group__8__Impl rule__RangedSpecifics__Group__9 ;
    public final void rule__RangedSpecifics__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3445:1: ( rule__RangedSpecifics__Group__8__Impl rule__RangedSpecifics__Group__9 )
            // InternalMazeDsl.g:3446:2: rule__RangedSpecifics__Group__8__Impl rule__RangedSpecifics__Group__9
            {
            pushFollow(FOLLOW_29);
            rule__RangedSpecifics__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__8"


    // $ANTLR start "rule__RangedSpecifics__Group__8__Impl"
    // InternalMazeDsl.g:3453:1: rule__RangedSpecifics__Group__8__Impl : ( ( rule__RangedSpecifics__Group_8__0 )? ) ;
    public final void rule__RangedSpecifics__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3457:1: ( ( ( rule__RangedSpecifics__Group_8__0 )? ) )
            // InternalMazeDsl.g:3458:1: ( ( rule__RangedSpecifics__Group_8__0 )? )
            {
            // InternalMazeDsl.g:3458:1: ( ( rule__RangedSpecifics__Group_8__0 )? )
            // InternalMazeDsl.g:3459:2: ( rule__RangedSpecifics__Group_8__0 )?
            {
             before(grammarAccess.getRangedSpecificsAccess().getGroup_8()); 
            // InternalMazeDsl.g:3460:2: ( rule__RangedSpecifics__Group_8__0 )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==63) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalMazeDsl.g:3460:3: rule__RangedSpecifics__Group_8__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RangedSpecifics__Group_8__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRangedSpecificsAccess().getGroup_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__8__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group__9"
    // InternalMazeDsl.g:3468:1: rule__RangedSpecifics__Group__9 : rule__RangedSpecifics__Group__9__Impl ;
    public final void rule__RangedSpecifics__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3472:1: ( rule__RangedSpecifics__Group__9__Impl )
            // InternalMazeDsl.g:3473:2: rule__RangedSpecifics__Group__9__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group__9__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__9"


    // $ANTLR start "rule__RangedSpecifics__Group__9__Impl"
    // InternalMazeDsl.g:3479:1: rule__RangedSpecifics__Group__9__Impl : ( '}' ) ;
    public final void rule__RangedSpecifics__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3483:1: ( ( '}' ) )
            // InternalMazeDsl.g:3484:1: ( '}' )
            {
            // InternalMazeDsl.g:3484:1: ( '}' )
            // InternalMazeDsl.g:3485:2: '}'
            {
             before(grammarAccess.getRangedSpecificsAccess().getRightCurlyBracketKeyword_9()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getRightCurlyBracketKeyword_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group__9__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_3__0"
    // InternalMazeDsl.g:3495:1: rule__RangedSpecifics__Group_3__0 : rule__RangedSpecifics__Group_3__0__Impl rule__RangedSpecifics__Group_3__1 ;
    public final void rule__RangedSpecifics__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3499:1: ( rule__RangedSpecifics__Group_3__0__Impl rule__RangedSpecifics__Group_3__1 )
            // InternalMazeDsl.g:3500:2: rule__RangedSpecifics__Group_3__0__Impl rule__RangedSpecifics__Group_3__1
            {
            pushFollow(FOLLOW_16);
            rule__RangedSpecifics__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_3__0"


    // $ANTLR start "rule__RangedSpecifics__Group_3__0__Impl"
    // InternalMazeDsl.g:3507:1: rule__RangedSpecifics__Group_3__0__Impl : ( 'attackRange' ) ;
    public final void rule__RangedSpecifics__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3511:1: ( ( 'attackRange' ) )
            // InternalMazeDsl.g:3512:1: ( 'attackRange' )
            {
            // InternalMazeDsl.g:3512:1: ( 'attackRange' )
            // InternalMazeDsl.g:3513:2: 'attackRange'
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackRangeKeyword_3_0()); 
            match(input,59,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getAttackRangeKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_3__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_3__1"
    // InternalMazeDsl.g:3522:1: rule__RangedSpecifics__Group_3__1 : rule__RangedSpecifics__Group_3__1__Impl ;
    public final void rule__RangedSpecifics__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3526:1: ( rule__RangedSpecifics__Group_3__1__Impl )
            // InternalMazeDsl.g:3527:2: rule__RangedSpecifics__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_3__1"


    // $ANTLR start "rule__RangedSpecifics__Group_3__1__Impl"
    // InternalMazeDsl.g:3533:1: rule__RangedSpecifics__Group_3__1__Impl : ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) ) ;
    public final void rule__RangedSpecifics__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3537:1: ( ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) ) )
            // InternalMazeDsl.g:3538:1: ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:3538:1: ( ( rule__RangedSpecifics__AttackRangeAssignment_3_1 ) )
            // InternalMazeDsl.g:3539:2: ( rule__RangedSpecifics__AttackRangeAssignment_3_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackRangeAssignment_3_1()); 
            // InternalMazeDsl.g:3540:2: ( rule__RangedSpecifics__AttackRangeAssignment_3_1 )
            // InternalMazeDsl.g:3540:3: rule__RangedSpecifics__AttackRangeAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__AttackRangeAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getAttackRangeAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_3__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_4__0"
    // InternalMazeDsl.g:3549:1: rule__RangedSpecifics__Group_4__0 : rule__RangedSpecifics__Group_4__0__Impl rule__RangedSpecifics__Group_4__1 ;
    public final void rule__RangedSpecifics__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3553:1: ( rule__RangedSpecifics__Group_4__0__Impl rule__RangedSpecifics__Group_4__1 )
            // InternalMazeDsl.g:3554:2: rule__RangedSpecifics__Group_4__0__Impl rule__RangedSpecifics__Group_4__1
            {
            pushFollow(FOLLOW_25);
            rule__RangedSpecifics__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_4__0"


    // $ANTLR start "rule__RangedSpecifics__Group_4__0__Impl"
    // InternalMazeDsl.g:3561:1: rule__RangedSpecifics__Group_4__0__Impl : ( 'attackCooldown' ) ;
    public final void rule__RangedSpecifics__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3565:1: ( ( 'attackCooldown' ) )
            // InternalMazeDsl.g:3566:1: ( 'attackCooldown' )
            {
            // InternalMazeDsl.g:3566:1: ( 'attackCooldown' )
            // InternalMazeDsl.g:3567:2: 'attackCooldown'
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackCooldownKeyword_4_0()); 
            match(input,60,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getAttackCooldownKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_4__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_4__1"
    // InternalMazeDsl.g:3576:1: rule__RangedSpecifics__Group_4__1 : rule__RangedSpecifics__Group_4__1__Impl ;
    public final void rule__RangedSpecifics__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3580:1: ( rule__RangedSpecifics__Group_4__1__Impl )
            // InternalMazeDsl.g:3581:2: rule__RangedSpecifics__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_4__1"


    // $ANTLR start "rule__RangedSpecifics__Group_4__1__Impl"
    // InternalMazeDsl.g:3587:1: rule__RangedSpecifics__Group_4__1__Impl : ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) ) ;
    public final void rule__RangedSpecifics__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3591:1: ( ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) ) )
            // InternalMazeDsl.g:3592:1: ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) )
            {
            // InternalMazeDsl.g:3592:1: ( ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 ) )
            // InternalMazeDsl.g:3593:2: ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackCooldownAssignment_4_1()); 
            // InternalMazeDsl.g:3594:2: ( rule__RangedSpecifics__AttackCooldownAssignment_4_1 )
            // InternalMazeDsl.g:3594:3: rule__RangedSpecifics__AttackCooldownAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__AttackCooldownAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getAttackCooldownAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_4__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_5__0"
    // InternalMazeDsl.g:3603:1: rule__RangedSpecifics__Group_5__0 : rule__RangedSpecifics__Group_5__0__Impl rule__RangedSpecifics__Group_5__1 ;
    public final void rule__RangedSpecifics__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3607:1: ( rule__RangedSpecifics__Group_5__0__Impl rule__RangedSpecifics__Group_5__1 )
            // InternalMazeDsl.g:3608:2: rule__RangedSpecifics__Group_5__0__Impl rule__RangedSpecifics__Group_5__1
            {
            pushFollow(FOLLOW_25);
            rule__RangedSpecifics__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_5__0"


    // $ANTLR start "rule__RangedSpecifics__Group_5__0__Impl"
    // InternalMazeDsl.g:3615:1: rule__RangedSpecifics__Group_5__0__Impl : ( 'attackDamage' ) ;
    public final void rule__RangedSpecifics__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3619:1: ( ( 'attackDamage' ) )
            // InternalMazeDsl.g:3620:1: ( 'attackDamage' )
            {
            // InternalMazeDsl.g:3620:1: ( 'attackDamage' )
            // InternalMazeDsl.g:3621:2: 'attackDamage'
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackDamageKeyword_5_0()); 
            match(input,52,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getAttackDamageKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_5__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_5__1"
    // InternalMazeDsl.g:3630:1: rule__RangedSpecifics__Group_5__1 : rule__RangedSpecifics__Group_5__1__Impl ;
    public final void rule__RangedSpecifics__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3634:1: ( rule__RangedSpecifics__Group_5__1__Impl )
            // InternalMazeDsl.g:3635:2: rule__RangedSpecifics__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_5__1"


    // $ANTLR start "rule__RangedSpecifics__Group_5__1__Impl"
    // InternalMazeDsl.g:3641:1: rule__RangedSpecifics__Group_5__1__Impl : ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) ) ;
    public final void rule__RangedSpecifics__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3645:1: ( ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) ) )
            // InternalMazeDsl.g:3646:1: ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:3646:1: ( ( rule__RangedSpecifics__AttackDamageAssignment_5_1 ) )
            // InternalMazeDsl.g:3647:2: ( rule__RangedSpecifics__AttackDamageAssignment_5_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackDamageAssignment_5_1()); 
            // InternalMazeDsl.g:3648:2: ( rule__RangedSpecifics__AttackDamageAssignment_5_1 )
            // InternalMazeDsl.g:3648:3: rule__RangedSpecifics__AttackDamageAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__AttackDamageAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getAttackDamageAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_5__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_6__0"
    // InternalMazeDsl.g:3657:1: rule__RangedSpecifics__Group_6__0 : rule__RangedSpecifics__Group_6__0__Impl rule__RangedSpecifics__Group_6__1 ;
    public final void rule__RangedSpecifics__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3661:1: ( rule__RangedSpecifics__Group_6__0__Impl rule__RangedSpecifics__Group_6__1 )
            // InternalMazeDsl.g:3662:2: rule__RangedSpecifics__Group_6__0__Impl rule__RangedSpecifics__Group_6__1
            {
            pushFollow(FOLLOW_16);
            rule__RangedSpecifics__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_6__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_6__0"


    // $ANTLR start "rule__RangedSpecifics__Group_6__0__Impl"
    // InternalMazeDsl.g:3669:1: rule__RangedSpecifics__Group_6__0__Impl : ( 'projectileSpeed' ) ;
    public final void rule__RangedSpecifics__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3673:1: ( ( 'projectileSpeed' ) )
            // InternalMazeDsl.g:3674:1: ( 'projectileSpeed' )
            {
            // InternalMazeDsl.g:3674:1: ( 'projectileSpeed' )
            // InternalMazeDsl.g:3675:2: 'projectileSpeed'
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedKeyword_6_0()); 
            match(input,61,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_6__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_6__1"
    // InternalMazeDsl.g:3684:1: rule__RangedSpecifics__Group_6__1 : rule__RangedSpecifics__Group_6__1__Impl ;
    public final void rule__RangedSpecifics__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3688:1: ( rule__RangedSpecifics__Group_6__1__Impl )
            // InternalMazeDsl.g:3689:2: rule__RangedSpecifics__Group_6__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_6__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_6__1"


    // $ANTLR start "rule__RangedSpecifics__Group_6__1__Impl"
    // InternalMazeDsl.g:3695:1: rule__RangedSpecifics__Group_6__1__Impl : ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) ) ;
    public final void rule__RangedSpecifics__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3699:1: ( ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) ) )
            // InternalMazeDsl.g:3700:1: ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) )
            {
            // InternalMazeDsl.g:3700:1: ( ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 ) )
            // InternalMazeDsl.g:3701:2: ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedAssignment_6_1()); 
            // InternalMazeDsl.g:3702:2: ( rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 )
            // InternalMazeDsl.g:3702:3: rule__RangedSpecifics__ProjectileSpeedAssignment_6_1
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__ProjectileSpeedAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedAssignment_6_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_6__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_7__0"
    // InternalMazeDsl.g:3711:1: rule__RangedSpecifics__Group_7__0 : rule__RangedSpecifics__Group_7__0__Impl rule__RangedSpecifics__Group_7__1 ;
    public final void rule__RangedSpecifics__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3715:1: ( rule__RangedSpecifics__Group_7__0__Impl rule__RangedSpecifics__Group_7__1 )
            // InternalMazeDsl.g:3716:2: rule__RangedSpecifics__Group_7__0__Impl rule__RangedSpecifics__Group_7__1
            {
            pushFollow(FOLLOW_30);
            rule__RangedSpecifics__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_7__0"


    // $ANTLR start "rule__RangedSpecifics__Group_7__0__Impl"
    // InternalMazeDsl.g:3723:1: rule__RangedSpecifics__Group_7__0__Impl : ( 'projectileType' ) ;
    public final void rule__RangedSpecifics__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3727:1: ( ( 'projectileType' ) )
            // InternalMazeDsl.g:3728:1: ( 'projectileType' )
            {
            // InternalMazeDsl.g:3728:1: ( 'projectileType' )
            // InternalMazeDsl.g:3729:2: 'projectileType'
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileTypeKeyword_7_0()); 
            match(input,62,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getProjectileTypeKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_7__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_7__1"
    // InternalMazeDsl.g:3738:1: rule__RangedSpecifics__Group_7__1 : rule__RangedSpecifics__Group_7__1__Impl ;
    public final void rule__RangedSpecifics__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3742:1: ( rule__RangedSpecifics__Group_7__1__Impl )
            // InternalMazeDsl.g:3743:2: rule__RangedSpecifics__Group_7__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_7__1"


    // $ANTLR start "rule__RangedSpecifics__Group_7__1__Impl"
    // InternalMazeDsl.g:3749:1: rule__RangedSpecifics__Group_7__1__Impl : ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) ) ;
    public final void rule__RangedSpecifics__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3753:1: ( ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) ) )
            // InternalMazeDsl.g:3754:1: ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:3754:1: ( ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 ) )
            // InternalMazeDsl.g:3755:2: ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileTypeAssignment_7_1()); 
            // InternalMazeDsl.g:3756:2: ( rule__RangedSpecifics__ProjectileTypeAssignment_7_1 )
            // InternalMazeDsl.g:3756:3: rule__RangedSpecifics__ProjectileTypeAssignment_7_1
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__ProjectileTypeAssignment_7_1();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getProjectileTypeAssignment_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_7__1__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_8__0"
    // InternalMazeDsl.g:3765:1: rule__RangedSpecifics__Group_8__0 : rule__RangedSpecifics__Group_8__0__Impl rule__RangedSpecifics__Group_8__1 ;
    public final void rule__RangedSpecifics__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3769:1: ( rule__RangedSpecifics__Group_8__0__Impl rule__RangedSpecifics__Group_8__1 )
            // InternalMazeDsl.g:3770:2: rule__RangedSpecifics__Group_8__0__Impl rule__RangedSpecifics__Group_8__1
            {
            pushFollow(FOLLOW_16);
            rule__RangedSpecifics__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_8__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_8__0"


    // $ANTLR start "rule__RangedSpecifics__Group_8__0__Impl"
    // InternalMazeDsl.g:3777:1: rule__RangedSpecifics__Group_8__0__Impl : ( 'splashRadius' ) ;
    public final void rule__RangedSpecifics__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3781:1: ( ( 'splashRadius' ) )
            // InternalMazeDsl.g:3782:1: ( 'splashRadius' )
            {
            // InternalMazeDsl.g:3782:1: ( 'splashRadius' )
            // InternalMazeDsl.g:3783:2: 'splashRadius'
            {
             before(grammarAccess.getRangedSpecificsAccess().getSplashRadiusKeyword_8_0()); 
            match(input,63,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getSplashRadiusKeyword_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_8__0__Impl"


    // $ANTLR start "rule__RangedSpecifics__Group_8__1"
    // InternalMazeDsl.g:3792:1: rule__RangedSpecifics__Group_8__1 : rule__RangedSpecifics__Group_8__1__Impl ;
    public final void rule__RangedSpecifics__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3796:1: ( rule__RangedSpecifics__Group_8__1__Impl )
            // InternalMazeDsl.g:3797:2: rule__RangedSpecifics__Group_8__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__Group_8__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_8__1"


    // $ANTLR start "rule__RangedSpecifics__Group_8__1__Impl"
    // InternalMazeDsl.g:3803:1: rule__RangedSpecifics__Group_8__1__Impl : ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) ) ;
    public final void rule__RangedSpecifics__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3807:1: ( ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) ) )
            // InternalMazeDsl.g:3808:1: ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:3808:1: ( ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 ) )
            // InternalMazeDsl.g:3809:2: ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 )
            {
             before(grammarAccess.getRangedSpecificsAccess().getSplashRadiusAssignment_8_1()); 
            // InternalMazeDsl.g:3810:2: ( rule__RangedSpecifics__SplashRadiusAssignment_8_1 )
            // InternalMazeDsl.g:3810:3: rule__RangedSpecifics__SplashRadiusAssignment_8_1
            {
            pushFollow(FOLLOW_2);
            rule__RangedSpecifics__SplashRadiusAssignment_8_1();

            state._fsp--;


            }

             after(grammarAccess.getRangedSpecificsAccess().getSplashRadiusAssignment_8_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__Group_8__1__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__0"
    // InternalMazeDsl.g:3819:1: rule__PatrolConfig__Group__0 : rule__PatrolConfig__Group__0__Impl rule__PatrolConfig__Group__1 ;
    public final void rule__PatrolConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3823:1: ( rule__PatrolConfig__Group__0__Impl rule__PatrolConfig__Group__1 )
            // InternalMazeDsl.g:3824:2: rule__PatrolConfig__Group__0__Impl rule__PatrolConfig__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__PatrolConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__0"


    // $ANTLR start "rule__PatrolConfig__Group__0__Impl"
    // InternalMazeDsl.g:3831:1: rule__PatrolConfig__Group__0__Impl : ( 'patrol' ) ;
    public final void rule__PatrolConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3835:1: ( ( 'patrol' ) )
            // InternalMazeDsl.g:3836:1: ( 'patrol' )
            {
            // InternalMazeDsl.g:3836:1: ( 'patrol' )
            // InternalMazeDsl.g:3837:2: 'patrol'
            {
             before(grammarAccess.getPatrolConfigAccess().getPatrolKeyword_0()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getPatrolKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__0__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__1"
    // InternalMazeDsl.g:3846:1: rule__PatrolConfig__Group__1 : rule__PatrolConfig__Group__1__Impl rule__PatrolConfig__Group__2 ;
    public final void rule__PatrolConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3850:1: ( rule__PatrolConfig__Group__1__Impl rule__PatrolConfig__Group__2 )
            // InternalMazeDsl.g:3851:2: rule__PatrolConfig__Group__1__Impl rule__PatrolConfig__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__PatrolConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__1"


    // $ANTLR start "rule__PatrolConfig__Group__1__Impl"
    // InternalMazeDsl.g:3858:1: rule__PatrolConfig__Group__1__Impl : ( ( rule__PatrolConfig__NameAssignment_1 ) ) ;
    public final void rule__PatrolConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3862:1: ( ( ( rule__PatrolConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:3863:1: ( ( rule__PatrolConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:3863:1: ( ( rule__PatrolConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:3864:2: ( rule__PatrolConfig__NameAssignment_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:3865:2: ( rule__PatrolConfig__NameAssignment_1 )
            // InternalMazeDsl.g:3865:3: rule__PatrolConfig__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getPatrolConfigAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__1__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__2"
    // InternalMazeDsl.g:3873:1: rule__PatrolConfig__Group__2 : rule__PatrolConfig__Group__2__Impl rule__PatrolConfig__Group__3 ;
    public final void rule__PatrolConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3877:1: ( rule__PatrolConfig__Group__2__Impl rule__PatrolConfig__Group__3 )
            // InternalMazeDsl.g:3878:2: rule__PatrolConfig__Group__2__Impl rule__PatrolConfig__Group__3
            {
            pushFollow(FOLLOW_31);
            rule__PatrolConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__2"


    // $ANTLR start "rule__PatrolConfig__Group__2__Impl"
    // InternalMazeDsl.g:3885:1: rule__PatrolConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__PatrolConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3889:1: ( ( '{' ) )
            // InternalMazeDsl.g:3890:1: ( '{' )
            {
            // InternalMazeDsl.g:3890:1: ( '{' )
            // InternalMazeDsl.g:3891:2: '{'
            {
             before(grammarAccess.getPatrolConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__2__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__3"
    // InternalMazeDsl.g:3900:1: rule__PatrolConfig__Group__3 : rule__PatrolConfig__Group__3__Impl rule__PatrolConfig__Group__4 ;
    public final void rule__PatrolConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3904:1: ( rule__PatrolConfig__Group__3__Impl rule__PatrolConfig__Group__4 )
            // InternalMazeDsl.g:3905:2: rule__PatrolConfig__Group__3__Impl rule__PatrolConfig__Group__4
            {
            pushFollow(FOLLOW_31);
            rule__PatrolConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__3"


    // $ANTLR start "rule__PatrolConfig__Group__3__Impl"
    // InternalMazeDsl.g:3912:1: rule__PatrolConfig__Group__3__Impl : ( ( rule__PatrolConfig__Group_3__0 )? ) ;
    public final void rule__PatrolConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3916:1: ( ( ( rule__PatrolConfig__Group_3__0 )? ) )
            // InternalMazeDsl.g:3917:1: ( ( rule__PatrolConfig__Group_3__0 )? )
            {
            // InternalMazeDsl.g:3917:1: ( ( rule__PatrolConfig__Group_3__0 )? )
            // InternalMazeDsl.g:3918:2: ( rule__PatrolConfig__Group_3__0 )?
            {
             before(grammarAccess.getPatrolConfigAccess().getGroup_3()); 
            // InternalMazeDsl.g:3919:2: ( rule__PatrolConfig__Group_3__0 )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==67) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalMazeDsl.g:3919:3: rule__PatrolConfig__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__PatrolConfig__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPatrolConfigAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__3__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__4"
    // InternalMazeDsl.g:3927:1: rule__PatrolConfig__Group__4 : rule__PatrolConfig__Group__4__Impl rule__PatrolConfig__Group__5 ;
    public final void rule__PatrolConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3931:1: ( rule__PatrolConfig__Group__4__Impl rule__PatrolConfig__Group__5 )
            // InternalMazeDsl.g:3932:2: rule__PatrolConfig__Group__4__Impl rule__PatrolConfig__Group__5
            {
            pushFollow(FOLLOW_31);
            rule__PatrolConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__4"


    // $ANTLR start "rule__PatrolConfig__Group__4__Impl"
    // InternalMazeDsl.g:3939:1: rule__PatrolConfig__Group__4__Impl : ( ( rule__PatrolConfig__ZoneAssignment_4 )? ) ;
    public final void rule__PatrolConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3943:1: ( ( ( rule__PatrolConfig__ZoneAssignment_4 )? ) )
            // InternalMazeDsl.g:3944:1: ( ( rule__PatrolConfig__ZoneAssignment_4 )? )
            {
            // InternalMazeDsl.g:3944:1: ( ( rule__PatrolConfig__ZoneAssignment_4 )? )
            // InternalMazeDsl.g:3945:2: ( rule__PatrolConfig__ZoneAssignment_4 )?
            {
             before(grammarAccess.getPatrolConfigAccess().getZoneAssignment_4()); 
            // InternalMazeDsl.g:3946:2: ( rule__PatrolConfig__ZoneAssignment_4 )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==69) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalMazeDsl.g:3946:3: rule__PatrolConfig__ZoneAssignment_4
                    {
                    pushFollow(FOLLOW_2);
                    rule__PatrolConfig__ZoneAssignment_4();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPatrolConfigAccess().getZoneAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__4__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__5"
    // InternalMazeDsl.g:3954:1: rule__PatrolConfig__Group__5 : rule__PatrolConfig__Group__5__Impl rule__PatrolConfig__Group__6 ;
    public final void rule__PatrolConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3958:1: ( rule__PatrolConfig__Group__5__Impl rule__PatrolConfig__Group__6 )
            // InternalMazeDsl.g:3959:2: rule__PatrolConfig__Group__5__Impl rule__PatrolConfig__Group__6
            {
            pushFollow(FOLLOW_32);
            rule__PatrolConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__5"


    // $ANTLR start "rule__PatrolConfig__Group__5__Impl"
    // InternalMazeDsl.g:3966:1: rule__PatrolConfig__Group__5__Impl : ( 'path' ) ;
    public final void rule__PatrolConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3970:1: ( ( 'path' ) )
            // InternalMazeDsl.g:3971:1: ( 'path' )
            {
            // InternalMazeDsl.g:3971:1: ( 'path' )
            // InternalMazeDsl.g:3972:2: 'path'
            {
             before(grammarAccess.getPatrolConfigAccess().getPathKeyword_5()); 
            match(input,64,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getPathKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__5__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__6"
    // InternalMazeDsl.g:3981:1: rule__PatrolConfig__Group__6 : rule__PatrolConfig__Group__6__Impl rule__PatrolConfig__Group__7 ;
    public final void rule__PatrolConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3985:1: ( rule__PatrolConfig__Group__6__Impl rule__PatrolConfig__Group__7 )
            // InternalMazeDsl.g:3986:2: rule__PatrolConfig__Group__6__Impl rule__PatrolConfig__Group__7
            {
            pushFollow(FOLLOW_33);
            rule__PatrolConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__6"


    // $ANTLR start "rule__PatrolConfig__Group__6__Impl"
    // InternalMazeDsl.g:3993:1: rule__PatrolConfig__Group__6__Impl : ( '[' ) ;
    public final void rule__PatrolConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:3997:1: ( ( '[' ) )
            // InternalMazeDsl.g:3998:1: ( '[' )
            {
            // InternalMazeDsl.g:3998:1: ( '[' )
            // InternalMazeDsl.g:3999:2: '['
            {
             before(grammarAccess.getPatrolConfigAccess().getLeftSquareBracketKeyword_6()); 
            match(input,65,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getLeftSquareBracketKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__6__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__7"
    // InternalMazeDsl.g:4008:1: rule__PatrolConfig__Group__7 : rule__PatrolConfig__Group__7__Impl rule__PatrolConfig__Group__8 ;
    public final void rule__PatrolConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4012:1: ( rule__PatrolConfig__Group__7__Impl rule__PatrolConfig__Group__8 )
            // InternalMazeDsl.g:4013:2: rule__PatrolConfig__Group__7__Impl rule__PatrolConfig__Group__8
            {
            pushFollow(FOLLOW_34);
            rule__PatrolConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__7"


    // $ANTLR start "rule__PatrolConfig__Group__7__Impl"
    // InternalMazeDsl.g:4020:1: rule__PatrolConfig__Group__7__Impl : ( ( rule__PatrolConfig__WaypointsAssignment_7 ) ) ;
    public final void rule__PatrolConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4024:1: ( ( ( rule__PatrolConfig__WaypointsAssignment_7 ) ) )
            // InternalMazeDsl.g:4025:1: ( ( rule__PatrolConfig__WaypointsAssignment_7 ) )
            {
            // InternalMazeDsl.g:4025:1: ( ( rule__PatrolConfig__WaypointsAssignment_7 ) )
            // InternalMazeDsl.g:4026:2: ( rule__PatrolConfig__WaypointsAssignment_7 )
            {
             before(grammarAccess.getPatrolConfigAccess().getWaypointsAssignment_7()); 
            // InternalMazeDsl.g:4027:2: ( rule__PatrolConfig__WaypointsAssignment_7 )
            // InternalMazeDsl.g:4027:3: rule__PatrolConfig__WaypointsAssignment_7
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__WaypointsAssignment_7();

            state._fsp--;


            }

             after(grammarAccess.getPatrolConfigAccess().getWaypointsAssignment_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__7__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__8"
    // InternalMazeDsl.g:4035:1: rule__PatrolConfig__Group__8 : rule__PatrolConfig__Group__8__Impl rule__PatrolConfig__Group__9 ;
    public final void rule__PatrolConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4039:1: ( rule__PatrolConfig__Group__8__Impl rule__PatrolConfig__Group__9 )
            // InternalMazeDsl.g:4040:2: rule__PatrolConfig__Group__8__Impl rule__PatrolConfig__Group__9
            {
            pushFollow(FOLLOW_34);
            rule__PatrolConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__8"


    // $ANTLR start "rule__PatrolConfig__Group__8__Impl"
    // InternalMazeDsl.g:4047:1: rule__PatrolConfig__Group__8__Impl : ( ( rule__PatrolConfig__Group_8__0 )* ) ;
    public final void rule__PatrolConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4051:1: ( ( ( rule__PatrolConfig__Group_8__0 )* ) )
            // InternalMazeDsl.g:4052:1: ( ( rule__PatrolConfig__Group_8__0 )* )
            {
            // InternalMazeDsl.g:4052:1: ( ( rule__PatrolConfig__Group_8__0 )* )
            // InternalMazeDsl.g:4053:2: ( rule__PatrolConfig__Group_8__0 )*
            {
             before(grammarAccess.getPatrolConfigAccess().getGroup_8()); 
            // InternalMazeDsl.g:4054:2: ( rule__PatrolConfig__Group_8__0 )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==68) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalMazeDsl.g:4054:3: rule__PatrolConfig__Group_8__0
            	    {
            	    pushFollow(FOLLOW_35);
            	    rule__PatrolConfig__Group_8__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);

             after(grammarAccess.getPatrolConfigAccess().getGroup_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__8__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__9"
    // InternalMazeDsl.g:4062:1: rule__PatrolConfig__Group__9 : rule__PatrolConfig__Group__9__Impl rule__PatrolConfig__Group__10 ;
    public final void rule__PatrolConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4066:1: ( rule__PatrolConfig__Group__9__Impl rule__PatrolConfig__Group__10 )
            // InternalMazeDsl.g:4067:2: rule__PatrolConfig__Group__9__Impl rule__PatrolConfig__Group__10
            {
            pushFollow(FOLLOW_36);
            rule__PatrolConfig__Group__9__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__10();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__9"


    // $ANTLR start "rule__PatrolConfig__Group__9__Impl"
    // InternalMazeDsl.g:4074:1: rule__PatrolConfig__Group__9__Impl : ( ']' ) ;
    public final void rule__PatrolConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4078:1: ( ( ']' ) )
            // InternalMazeDsl.g:4079:1: ( ']' )
            {
            // InternalMazeDsl.g:4079:1: ( ']' )
            // InternalMazeDsl.g:4080:2: ']'
            {
             before(grammarAccess.getPatrolConfigAccess().getRightSquareBracketKeyword_9()); 
            match(input,66,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getRightSquareBracketKeyword_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__9__Impl"


    // $ANTLR start "rule__PatrolConfig__Group__10"
    // InternalMazeDsl.g:4089:1: rule__PatrolConfig__Group__10 : rule__PatrolConfig__Group__10__Impl ;
    public final void rule__PatrolConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4093:1: ( rule__PatrolConfig__Group__10__Impl )
            // InternalMazeDsl.g:4094:2: rule__PatrolConfig__Group__10__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group__10__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__10"


    // $ANTLR start "rule__PatrolConfig__Group__10__Impl"
    // InternalMazeDsl.g:4100:1: rule__PatrolConfig__Group__10__Impl : ( '}' ) ;
    public final void rule__PatrolConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4104:1: ( ( '}' ) )
            // InternalMazeDsl.g:4105:1: ( '}' )
            {
            // InternalMazeDsl.g:4105:1: ( '}' )
            // InternalMazeDsl.g:4106:2: '}'
            {
             before(grammarAccess.getPatrolConfigAccess().getRightCurlyBracketKeyword_10()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getRightCurlyBracketKeyword_10()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group__10__Impl"


    // $ANTLR start "rule__PatrolConfig__Group_3__0"
    // InternalMazeDsl.g:4116:1: rule__PatrolConfig__Group_3__0 : rule__PatrolConfig__Group_3__0__Impl rule__PatrolConfig__Group_3__1 ;
    public final void rule__PatrolConfig__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4120:1: ( rule__PatrolConfig__Group_3__0__Impl rule__PatrolConfig__Group_3__1 )
            // InternalMazeDsl.g:4121:2: rule__PatrolConfig__Group_3__0__Impl rule__PatrolConfig__Group_3__1
            {
            pushFollow(FOLLOW_16);
            rule__PatrolConfig__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_3__0"


    // $ANTLR start "rule__PatrolConfig__Group_3__0__Impl"
    // InternalMazeDsl.g:4128:1: rule__PatrolConfig__Group_3__0__Impl : ( 'visionRange' ) ;
    public final void rule__PatrolConfig__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4132:1: ( ( 'visionRange' ) )
            // InternalMazeDsl.g:4133:1: ( 'visionRange' )
            {
            // InternalMazeDsl.g:4133:1: ( 'visionRange' )
            // InternalMazeDsl.g:4134:2: 'visionRange'
            {
             before(grammarAccess.getPatrolConfigAccess().getVisionRangeKeyword_3_0()); 
            match(input,67,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getVisionRangeKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_3__0__Impl"


    // $ANTLR start "rule__PatrolConfig__Group_3__1"
    // InternalMazeDsl.g:4143:1: rule__PatrolConfig__Group_3__1 : rule__PatrolConfig__Group_3__1__Impl ;
    public final void rule__PatrolConfig__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4147:1: ( rule__PatrolConfig__Group_3__1__Impl )
            // InternalMazeDsl.g:4148:2: rule__PatrolConfig__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_3__1"


    // $ANTLR start "rule__PatrolConfig__Group_3__1__Impl"
    // InternalMazeDsl.g:4154:1: rule__PatrolConfig__Group_3__1__Impl : ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) ) ;
    public final void rule__PatrolConfig__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4158:1: ( ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) ) )
            // InternalMazeDsl.g:4159:1: ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:4159:1: ( ( rule__PatrolConfig__VisionRangeAssignment_3_1 ) )
            // InternalMazeDsl.g:4160:2: ( rule__PatrolConfig__VisionRangeAssignment_3_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getVisionRangeAssignment_3_1()); 
            // InternalMazeDsl.g:4161:2: ( rule__PatrolConfig__VisionRangeAssignment_3_1 )
            // InternalMazeDsl.g:4161:3: rule__PatrolConfig__VisionRangeAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__VisionRangeAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getPatrolConfigAccess().getVisionRangeAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_3__1__Impl"


    // $ANTLR start "rule__PatrolConfig__Group_8__0"
    // InternalMazeDsl.g:4170:1: rule__PatrolConfig__Group_8__0 : rule__PatrolConfig__Group_8__0__Impl rule__PatrolConfig__Group_8__1 ;
    public final void rule__PatrolConfig__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4174:1: ( rule__PatrolConfig__Group_8__0__Impl rule__PatrolConfig__Group_8__1 )
            // InternalMazeDsl.g:4175:2: rule__PatrolConfig__Group_8__0__Impl rule__PatrolConfig__Group_8__1
            {
            pushFollow(FOLLOW_33);
            rule__PatrolConfig__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group_8__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_8__0"


    // $ANTLR start "rule__PatrolConfig__Group_8__0__Impl"
    // InternalMazeDsl.g:4182:1: rule__PatrolConfig__Group_8__0__Impl : ( ',' ) ;
    public final void rule__PatrolConfig__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4186:1: ( ( ',' ) )
            // InternalMazeDsl.g:4187:1: ( ',' )
            {
            // InternalMazeDsl.g:4187:1: ( ',' )
            // InternalMazeDsl.g:4188:2: ','
            {
             before(grammarAccess.getPatrolConfigAccess().getCommaKeyword_8_0()); 
            match(input,68,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getCommaKeyword_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_8__0__Impl"


    // $ANTLR start "rule__PatrolConfig__Group_8__1"
    // InternalMazeDsl.g:4197:1: rule__PatrolConfig__Group_8__1 : rule__PatrolConfig__Group_8__1__Impl ;
    public final void rule__PatrolConfig__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4201:1: ( rule__PatrolConfig__Group_8__1__Impl )
            // InternalMazeDsl.g:4202:2: rule__PatrolConfig__Group_8__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__Group_8__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_8__1"


    // $ANTLR start "rule__PatrolConfig__Group_8__1__Impl"
    // InternalMazeDsl.g:4208:1: rule__PatrolConfig__Group_8__1__Impl : ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) ) ;
    public final void rule__PatrolConfig__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4212:1: ( ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) ) )
            // InternalMazeDsl.g:4213:1: ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) )
            {
            // InternalMazeDsl.g:4213:1: ( ( rule__PatrolConfig__WaypointsAssignment_8_1 ) )
            // InternalMazeDsl.g:4214:2: ( rule__PatrolConfig__WaypointsAssignment_8_1 )
            {
             before(grammarAccess.getPatrolConfigAccess().getWaypointsAssignment_8_1()); 
            // InternalMazeDsl.g:4215:2: ( rule__PatrolConfig__WaypointsAssignment_8_1 )
            // InternalMazeDsl.g:4215:3: rule__PatrolConfig__WaypointsAssignment_8_1
            {
            pushFollow(FOLLOW_2);
            rule__PatrolConfig__WaypointsAssignment_8_1();

            state._fsp--;


            }

             after(grammarAccess.getPatrolConfigAccess().getWaypointsAssignment_8_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__Group_8__1__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__0"
    // InternalMazeDsl.g:4224:1: rule__PatrolZoneConfig__Group__0 : rule__PatrolZoneConfig__Group__0__Impl rule__PatrolZoneConfig__Group__1 ;
    public final void rule__PatrolZoneConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4228:1: ( rule__PatrolZoneConfig__Group__0__Impl rule__PatrolZoneConfig__Group__1 )
            // InternalMazeDsl.g:4229:2: rule__PatrolZoneConfig__Group__0__Impl rule__PatrolZoneConfig__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__PatrolZoneConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__0"


    // $ANTLR start "rule__PatrolZoneConfig__Group__0__Impl"
    // InternalMazeDsl.g:4236:1: rule__PatrolZoneConfig__Group__0__Impl : ( 'zone' ) ;
    public final void rule__PatrolZoneConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4240:1: ( ( 'zone' ) )
            // InternalMazeDsl.g:4241:1: ( 'zone' )
            {
            // InternalMazeDsl.g:4241:1: ( 'zone' )
            // InternalMazeDsl.g:4242:2: 'zone'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getZoneKeyword_0()); 
            match(input,69,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getZoneKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__0__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__1"
    // InternalMazeDsl.g:4251:1: rule__PatrolZoneConfig__Group__1 : rule__PatrolZoneConfig__Group__1__Impl rule__PatrolZoneConfig__Group__2 ;
    public final void rule__PatrolZoneConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4255:1: ( rule__PatrolZoneConfig__Group__1__Impl rule__PatrolZoneConfig__Group__2 )
            // InternalMazeDsl.g:4256:2: rule__PatrolZoneConfig__Group__1__Impl rule__PatrolZoneConfig__Group__2
            {
            pushFollow(FOLLOW_37);
            rule__PatrolZoneConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__1"


    // $ANTLR start "rule__PatrolZoneConfig__Group__1__Impl"
    // InternalMazeDsl.g:4263:1: rule__PatrolZoneConfig__Group__1__Impl : ( '{' ) ;
    public final void rule__PatrolZoneConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4267:1: ( ( '{' ) )
            // InternalMazeDsl.g:4268:1: ( '{' )
            {
            // InternalMazeDsl.g:4268:1: ( '{' )
            // InternalMazeDsl.g:4269:2: '{'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getLeftCurlyBracketKeyword_1()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getLeftCurlyBracketKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__1__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__2"
    // InternalMazeDsl.g:4278:1: rule__PatrolZoneConfig__Group__2 : rule__PatrolZoneConfig__Group__2__Impl rule__PatrolZoneConfig__Group__3 ;
    public final void rule__PatrolZoneConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4282:1: ( rule__PatrolZoneConfig__Group__2__Impl rule__PatrolZoneConfig__Group__3 )
            // InternalMazeDsl.g:4283:2: rule__PatrolZoneConfig__Group__2__Impl rule__PatrolZoneConfig__Group__3
            {
            pushFollow(FOLLOW_33);
            rule__PatrolZoneConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__2"


    // $ANTLR start "rule__PatrolZoneConfig__Group__2__Impl"
    // InternalMazeDsl.g:4290:1: rule__PatrolZoneConfig__Group__2__Impl : ( 'topLeft' ) ;
    public final void rule__PatrolZoneConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4294:1: ( ( 'topLeft' ) )
            // InternalMazeDsl.g:4295:1: ( 'topLeft' )
            {
            // InternalMazeDsl.g:4295:1: ( 'topLeft' )
            // InternalMazeDsl.g:4296:2: 'topLeft'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftKeyword_2()); 
            match(input,70,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getTopLeftKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__2__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__3"
    // InternalMazeDsl.g:4305:1: rule__PatrolZoneConfig__Group__3 : rule__PatrolZoneConfig__Group__3__Impl rule__PatrolZoneConfig__Group__4 ;
    public final void rule__PatrolZoneConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4309:1: ( rule__PatrolZoneConfig__Group__3__Impl rule__PatrolZoneConfig__Group__4 )
            // InternalMazeDsl.g:4310:2: rule__PatrolZoneConfig__Group__3__Impl rule__PatrolZoneConfig__Group__4
            {
            pushFollow(FOLLOW_16);
            rule__PatrolZoneConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__3"


    // $ANTLR start "rule__PatrolZoneConfig__Group__3__Impl"
    // InternalMazeDsl.g:4317:1: rule__PatrolZoneConfig__Group__3__Impl : ( '(' ) ;
    public final void rule__PatrolZoneConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4321:1: ( ( '(' ) )
            // InternalMazeDsl.g:4322:1: ( '(' )
            {
            // InternalMazeDsl.g:4322:1: ( '(' )
            // InternalMazeDsl.g:4323:2: '('
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getLeftParenthesisKeyword_3()); 
            match(input,71,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getLeftParenthesisKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__3__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__4"
    // InternalMazeDsl.g:4332:1: rule__PatrolZoneConfig__Group__4 : rule__PatrolZoneConfig__Group__4__Impl rule__PatrolZoneConfig__Group__5 ;
    public final void rule__PatrolZoneConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4336:1: ( rule__PatrolZoneConfig__Group__4__Impl rule__PatrolZoneConfig__Group__5 )
            // InternalMazeDsl.g:4337:2: rule__PatrolZoneConfig__Group__4__Impl rule__PatrolZoneConfig__Group__5
            {
            pushFollow(FOLLOW_38);
            rule__PatrolZoneConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__4"


    // $ANTLR start "rule__PatrolZoneConfig__Group__4__Impl"
    // InternalMazeDsl.g:4344:1: rule__PatrolZoneConfig__Group__4__Impl : ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) ) ;
    public final void rule__PatrolZoneConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4348:1: ( ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) ) )
            // InternalMazeDsl.g:4349:1: ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) )
            {
            // InternalMazeDsl.g:4349:1: ( ( rule__PatrolZoneConfig__TopLeftXAssignment_4 ) )
            // InternalMazeDsl.g:4350:2: ( rule__PatrolZoneConfig__TopLeftXAssignment_4 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXAssignment_4()); 
            // InternalMazeDsl.g:4351:2: ( rule__PatrolZoneConfig__TopLeftXAssignment_4 )
            // InternalMazeDsl.g:4351:3: rule__PatrolZoneConfig__TopLeftXAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__TopLeftXAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__4__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__5"
    // InternalMazeDsl.g:4359:1: rule__PatrolZoneConfig__Group__5 : rule__PatrolZoneConfig__Group__5__Impl rule__PatrolZoneConfig__Group__6 ;
    public final void rule__PatrolZoneConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4363:1: ( rule__PatrolZoneConfig__Group__5__Impl rule__PatrolZoneConfig__Group__6 )
            // InternalMazeDsl.g:4364:2: rule__PatrolZoneConfig__Group__5__Impl rule__PatrolZoneConfig__Group__6
            {
            pushFollow(FOLLOW_16);
            rule__PatrolZoneConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__5"


    // $ANTLR start "rule__PatrolZoneConfig__Group__5__Impl"
    // InternalMazeDsl.g:4371:1: rule__PatrolZoneConfig__Group__5__Impl : ( ',' ) ;
    public final void rule__PatrolZoneConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4375:1: ( ( ',' ) )
            // InternalMazeDsl.g:4376:1: ( ',' )
            {
            // InternalMazeDsl.g:4376:1: ( ',' )
            // InternalMazeDsl.g:4377:2: ','
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getCommaKeyword_5()); 
            match(input,68,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getCommaKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__5__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__6"
    // InternalMazeDsl.g:4386:1: rule__PatrolZoneConfig__Group__6 : rule__PatrolZoneConfig__Group__6__Impl rule__PatrolZoneConfig__Group__7 ;
    public final void rule__PatrolZoneConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4390:1: ( rule__PatrolZoneConfig__Group__6__Impl rule__PatrolZoneConfig__Group__7 )
            // InternalMazeDsl.g:4391:2: rule__PatrolZoneConfig__Group__6__Impl rule__PatrolZoneConfig__Group__7
            {
            pushFollow(FOLLOW_39);
            rule__PatrolZoneConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__6"


    // $ANTLR start "rule__PatrolZoneConfig__Group__6__Impl"
    // InternalMazeDsl.g:4398:1: rule__PatrolZoneConfig__Group__6__Impl : ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) ) ;
    public final void rule__PatrolZoneConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4402:1: ( ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) ) )
            // InternalMazeDsl.g:4403:1: ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) )
            {
            // InternalMazeDsl.g:4403:1: ( ( rule__PatrolZoneConfig__TopLeftYAssignment_6 ) )
            // InternalMazeDsl.g:4404:2: ( rule__PatrolZoneConfig__TopLeftYAssignment_6 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYAssignment_6()); 
            // InternalMazeDsl.g:4405:2: ( rule__PatrolZoneConfig__TopLeftYAssignment_6 )
            // InternalMazeDsl.g:4405:3: rule__PatrolZoneConfig__TopLeftYAssignment_6
            {
            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__TopLeftYAssignment_6();

            state._fsp--;


            }

             after(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYAssignment_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__6__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__7"
    // InternalMazeDsl.g:4413:1: rule__PatrolZoneConfig__Group__7 : rule__PatrolZoneConfig__Group__7__Impl rule__PatrolZoneConfig__Group__8 ;
    public final void rule__PatrolZoneConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4417:1: ( rule__PatrolZoneConfig__Group__7__Impl rule__PatrolZoneConfig__Group__8 )
            // InternalMazeDsl.g:4418:2: rule__PatrolZoneConfig__Group__7__Impl rule__PatrolZoneConfig__Group__8
            {
            pushFollow(FOLLOW_40);
            rule__PatrolZoneConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__7"


    // $ANTLR start "rule__PatrolZoneConfig__Group__7__Impl"
    // InternalMazeDsl.g:4425:1: rule__PatrolZoneConfig__Group__7__Impl : ( ')' ) ;
    public final void rule__PatrolZoneConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4429:1: ( ( ')' ) )
            // InternalMazeDsl.g:4430:1: ( ')' )
            {
            // InternalMazeDsl.g:4430:1: ( ')' )
            // InternalMazeDsl.g:4431:2: ')'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getRightParenthesisKeyword_7()); 
            match(input,72,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getRightParenthesisKeyword_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__7__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__8"
    // InternalMazeDsl.g:4440:1: rule__PatrolZoneConfig__Group__8 : rule__PatrolZoneConfig__Group__8__Impl rule__PatrolZoneConfig__Group__9 ;
    public final void rule__PatrolZoneConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4444:1: ( rule__PatrolZoneConfig__Group__8__Impl rule__PatrolZoneConfig__Group__9 )
            // InternalMazeDsl.g:4445:2: rule__PatrolZoneConfig__Group__8__Impl rule__PatrolZoneConfig__Group__9
            {
            pushFollow(FOLLOW_16);
            rule__PatrolZoneConfig__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__8"


    // $ANTLR start "rule__PatrolZoneConfig__Group__8__Impl"
    // InternalMazeDsl.g:4452:1: rule__PatrolZoneConfig__Group__8__Impl : ( 'width' ) ;
    public final void rule__PatrolZoneConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4456:1: ( ( 'width' ) )
            // InternalMazeDsl.g:4457:1: ( 'width' )
            {
            // InternalMazeDsl.g:4457:1: ( 'width' )
            // InternalMazeDsl.g:4458:2: 'width'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getWidthKeyword_8()); 
            match(input,73,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getWidthKeyword_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__8__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__9"
    // InternalMazeDsl.g:4467:1: rule__PatrolZoneConfig__Group__9 : rule__PatrolZoneConfig__Group__9__Impl rule__PatrolZoneConfig__Group__10 ;
    public final void rule__PatrolZoneConfig__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4471:1: ( rule__PatrolZoneConfig__Group__9__Impl rule__PatrolZoneConfig__Group__10 )
            // InternalMazeDsl.g:4472:2: rule__PatrolZoneConfig__Group__9__Impl rule__PatrolZoneConfig__Group__10
            {
            pushFollow(FOLLOW_41);
            rule__PatrolZoneConfig__Group__9__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__10();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__9"


    // $ANTLR start "rule__PatrolZoneConfig__Group__9__Impl"
    // InternalMazeDsl.g:4479:1: rule__PatrolZoneConfig__Group__9__Impl : ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) ) ;
    public final void rule__PatrolZoneConfig__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4483:1: ( ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) ) )
            // InternalMazeDsl.g:4484:1: ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) )
            {
            // InternalMazeDsl.g:4484:1: ( ( rule__PatrolZoneConfig__WidthAssignment_9 ) )
            // InternalMazeDsl.g:4485:2: ( rule__PatrolZoneConfig__WidthAssignment_9 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getWidthAssignment_9()); 
            // InternalMazeDsl.g:4486:2: ( rule__PatrolZoneConfig__WidthAssignment_9 )
            // InternalMazeDsl.g:4486:3: rule__PatrolZoneConfig__WidthAssignment_9
            {
            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__WidthAssignment_9();

            state._fsp--;


            }

             after(grammarAccess.getPatrolZoneConfigAccess().getWidthAssignment_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__9__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__10"
    // InternalMazeDsl.g:4494:1: rule__PatrolZoneConfig__Group__10 : rule__PatrolZoneConfig__Group__10__Impl rule__PatrolZoneConfig__Group__11 ;
    public final void rule__PatrolZoneConfig__Group__10() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4498:1: ( rule__PatrolZoneConfig__Group__10__Impl rule__PatrolZoneConfig__Group__11 )
            // InternalMazeDsl.g:4499:2: rule__PatrolZoneConfig__Group__10__Impl rule__PatrolZoneConfig__Group__11
            {
            pushFollow(FOLLOW_16);
            rule__PatrolZoneConfig__Group__10__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__11();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__10"


    // $ANTLR start "rule__PatrolZoneConfig__Group__10__Impl"
    // InternalMazeDsl.g:4506:1: rule__PatrolZoneConfig__Group__10__Impl : ( 'height' ) ;
    public final void rule__PatrolZoneConfig__Group__10__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4510:1: ( ( 'height' ) )
            // InternalMazeDsl.g:4511:1: ( 'height' )
            {
            // InternalMazeDsl.g:4511:1: ( 'height' )
            // InternalMazeDsl.g:4512:2: 'height'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getHeightKeyword_10()); 
            match(input,74,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getHeightKeyword_10()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__10__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__11"
    // InternalMazeDsl.g:4521:1: rule__PatrolZoneConfig__Group__11 : rule__PatrolZoneConfig__Group__11__Impl rule__PatrolZoneConfig__Group__12 ;
    public final void rule__PatrolZoneConfig__Group__11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4525:1: ( rule__PatrolZoneConfig__Group__11__Impl rule__PatrolZoneConfig__Group__12 )
            // InternalMazeDsl.g:4526:2: rule__PatrolZoneConfig__Group__11__Impl rule__PatrolZoneConfig__Group__12
            {
            pushFollow(FOLLOW_36);
            rule__PatrolZoneConfig__Group__11__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__12();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__11"


    // $ANTLR start "rule__PatrolZoneConfig__Group__11__Impl"
    // InternalMazeDsl.g:4533:1: rule__PatrolZoneConfig__Group__11__Impl : ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) ) ;
    public final void rule__PatrolZoneConfig__Group__11__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4537:1: ( ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) ) )
            // InternalMazeDsl.g:4538:1: ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) )
            {
            // InternalMazeDsl.g:4538:1: ( ( rule__PatrolZoneConfig__HeightAssignment_11 ) )
            // InternalMazeDsl.g:4539:2: ( rule__PatrolZoneConfig__HeightAssignment_11 )
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getHeightAssignment_11()); 
            // InternalMazeDsl.g:4540:2: ( rule__PatrolZoneConfig__HeightAssignment_11 )
            // InternalMazeDsl.g:4540:3: rule__PatrolZoneConfig__HeightAssignment_11
            {
            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__HeightAssignment_11();

            state._fsp--;


            }

             after(grammarAccess.getPatrolZoneConfigAccess().getHeightAssignment_11()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__11__Impl"


    // $ANTLR start "rule__PatrolZoneConfig__Group__12"
    // InternalMazeDsl.g:4548:1: rule__PatrolZoneConfig__Group__12 : rule__PatrolZoneConfig__Group__12__Impl ;
    public final void rule__PatrolZoneConfig__Group__12() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4552:1: ( rule__PatrolZoneConfig__Group__12__Impl )
            // InternalMazeDsl.g:4553:2: rule__PatrolZoneConfig__Group__12__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PatrolZoneConfig__Group__12__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__12"


    // $ANTLR start "rule__PatrolZoneConfig__Group__12__Impl"
    // InternalMazeDsl.g:4559:1: rule__PatrolZoneConfig__Group__12__Impl : ( '}' ) ;
    public final void rule__PatrolZoneConfig__Group__12__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4563:1: ( ( '}' ) )
            // InternalMazeDsl.g:4564:1: ( '}' )
            {
            // InternalMazeDsl.g:4564:1: ( '}' )
            // InternalMazeDsl.g:4565:2: '}'
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getRightCurlyBracketKeyword_12()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getPatrolZoneConfigAccess().getRightCurlyBracketKeyword_12()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__Group__12__Impl"


    // $ANTLR start "rule__Waypoint__Group__0"
    // InternalMazeDsl.g:4575:1: rule__Waypoint__Group__0 : rule__Waypoint__Group__0__Impl rule__Waypoint__Group__1 ;
    public final void rule__Waypoint__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4579:1: ( rule__Waypoint__Group__0__Impl rule__Waypoint__Group__1 )
            // InternalMazeDsl.g:4580:2: rule__Waypoint__Group__0__Impl rule__Waypoint__Group__1
            {
            pushFollow(FOLLOW_16);
            rule__Waypoint__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__0"


    // $ANTLR start "rule__Waypoint__Group__0__Impl"
    // InternalMazeDsl.g:4587:1: rule__Waypoint__Group__0__Impl : ( '(' ) ;
    public final void rule__Waypoint__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4591:1: ( ( '(' ) )
            // InternalMazeDsl.g:4592:1: ( '(' )
            {
            // InternalMazeDsl.g:4592:1: ( '(' )
            // InternalMazeDsl.g:4593:2: '('
            {
             before(grammarAccess.getWaypointAccess().getLeftParenthesisKeyword_0()); 
            match(input,71,FOLLOW_2); 
             after(grammarAccess.getWaypointAccess().getLeftParenthesisKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__0__Impl"


    // $ANTLR start "rule__Waypoint__Group__1"
    // InternalMazeDsl.g:4602:1: rule__Waypoint__Group__1 : rule__Waypoint__Group__1__Impl rule__Waypoint__Group__2 ;
    public final void rule__Waypoint__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4606:1: ( rule__Waypoint__Group__1__Impl rule__Waypoint__Group__2 )
            // InternalMazeDsl.g:4607:2: rule__Waypoint__Group__1__Impl rule__Waypoint__Group__2
            {
            pushFollow(FOLLOW_38);
            rule__Waypoint__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__1"


    // $ANTLR start "rule__Waypoint__Group__1__Impl"
    // InternalMazeDsl.g:4614:1: rule__Waypoint__Group__1__Impl : ( ( rule__Waypoint__XAssignment_1 ) ) ;
    public final void rule__Waypoint__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4618:1: ( ( ( rule__Waypoint__XAssignment_1 ) ) )
            // InternalMazeDsl.g:4619:1: ( ( rule__Waypoint__XAssignment_1 ) )
            {
            // InternalMazeDsl.g:4619:1: ( ( rule__Waypoint__XAssignment_1 ) )
            // InternalMazeDsl.g:4620:2: ( rule__Waypoint__XAssignment_1 )
            {
             before(grammarAccess.getWaypointAccess().getXAssignment_1()); 
            // InternalMazeDsl.g:4621:2: ( rule__Waypoint__XAssignment_1 )
            // InternalMazeDsl.g:4621:3: rule__Waypoint__XAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Waypoint__XAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getWaypointAccess().getXAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__1__Impl"


    // $ANTLR start "rule__Waypoint__Group__2"
    // InternalMazeDsl.g:4629:1: rule__Waypoint__Group__2 : rule__Waypoint__Group__2__Impl rule__Waypoint__Group__3 ;
    public final void rule__Waypoint__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4633:1: ( rule__Waypoint__Group__2__Impl rule__Waypoint__Group__3 )
            // InternalMazeDsl.g:4634:2: rule__Waypoint__Group__2__Impl rule__Waypoint__Group__3
            {
            pushFollow(FOLLOW_16);
            rule__Waypoint__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__2"


    // $ANTLR start "rule__Waypoint__Group__2__Impl"
    // InternalMazeDsl.g:4641:1: rule__Waypoint__Group__2__Impl : ( ',' ) ;
    public final void rule__Waypoint__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4645:1: ( ( ',' ) )
            // InternalMazeDsl.g:4646:1: ( ',' )
            {
            // InternalMazeDsl.g:4646:1: ( ',' )
            // InternalMazeDsl.g:4647:2: ','
            {
             before(grammarAccess.getWaypointAccess().getCommaKeyword_2()); 
            match(input,68,FOLLOW_2); 
             after(grammarAccess.getWaypointAccess().getCommaKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__2__Impl"


    // $ANTLR start "rule__Waypoint__Group__3"
    // InternalMazeDsl.g:4656:1: rule__Waypoint__Group__3 : rule__Waypoint__Group__3__Impl rule__Waypoint__Group__4 ;
    public final void rule__Waypoint__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4660:1: ( rule__Waypoint__Group__3__Impl rule__Waypoint__Group__4 )
            // InternalMazeDsl.g:4661:2: rule__Waypoint__Group__3__Impl rule__Waypoint__Group__4
            {
            pushFollow(FOLLOW_39);
            rule__Waypoint__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__3"


    // $ANTLR start "rule__Waypoint__Group__3__Impl"
    // InternalMazeDsl.g:4668:1: rule__Waypoint__Group__3__Impl : ( ( rule__Waypoint__YAssignment_3 ) ) ;
    public final void rule__Waypoint__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4672:1: ( ( ( rule__Waypoint__YAssignment_3 ) ) )
            // InternalMazeDsl.g:4673:1: ( ( rule__Waypoint__YAssignment_3 ) )
            {
            // InternalMazeDsl.g:4673:1: ( ( rule__Waypoint__YAssignment_3 ) )
            // InternalMazeDsl.g:4674:2: ( rule__Waypoint__YAssignment_3 )
            {
             before(grammarAccess.getWaypointAccess().getYAssignment_3()); 
            // InternalMazeDsl.g:4675:2: ( rule__Waypoint__YAssignment_3 )
            // InternalMazeDsl.g:4675:3: rule__Waypoint__YAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Waypoint__YAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getWaypointAccess().getYAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__3__Impl"


    // $ANTLR start "rule__Waypoint__Group__4"
    // InternalMazeDsl.g:4683:1: rule__Waypoint__Group__4 : rule__Waypoint__Group__4__Impl rule__Waypoint__Group__5 ;
    public final void rule__Waypoint__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4687:1: ( rule__Waypoint__Group__4__Impl rule__Waypoint__Group__5 )
            // InternalMazeDsl.g:4688:2: rule__Waypoint__Group__4__Impl rule__Waypoint__Group__5
            {
            pushFollow(FOLLOW_42);
            rule__Waypoint__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__4"


    // $ANTLR start "rule__Waypoint__Group__4__Impl"
    // InternalMazeDsl.g:4695:1: rule__Waypoint__Group__4__Impl : ( ')' ) ;
    public final void rule__Waypoint__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4699:1: ( ( ')' ) )
            // InternalMazeDsl.g:4700:1: ( ')' )
            {
            // InternalMazeDsl.g:4700:1: ( ')' )
            // InternalMazeDsl.g:4701:2: ')'
            {
             before(grammarAccess.getWaypointAccess().getRightParenthesisKeyword_4()); 
            match(input,72,FOLLOW_2); 
             after(grammarAccess.getWaypointAccess().getRightParenthesisKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__4__Impl"


    // $ANTLR start "rule__Waypoint__Group__5"
    // InternalMazeDsl.g:4710:1: rule__Waypoint__Group__5 : rule__Waypoint__Group__5__Impl ;
    public final void rule__Waypoint__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4714:1: ( rule__Waypoint__Group__5__Impl )
            // InternalMazeDsl.g:4715:2: rule__Waypoint__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Waypoint__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__5"


    // $ANTLR start "rule__Waypoint__Group__5__Impl"
    // InternalMazeDsl.g:4721:1: rule__Waypoint__Group__5__Impl : ( ( rule__Waypoint__Group_5__0 )? ) ;
    public final void rule__Waypoint__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4725:1: ( ( ( rule__Waypoint__Group_5__0 )? ) )
            // InternalMazeDsl.g:4726:1: ( ( rule__Waypoint__Group_5__0 )? )
            {
            // InternalMazeDsl.g:4726:1: ( ( rule__Waypoint__Group_5__0 )? )
            // InternalMazeDsl.g:4727:2: ( rule__Waypoint__Group_5__0 )?
            {
             before(grammarAccess.getWaypointAccess().getGroup_5()); 
            // InternalMazeDsl.g:4728:2: ( rule__Waypoint__Group_5__0 )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==75) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalMazeDsl.g:4728:3: rule__Waypoint__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Waypoint__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getWaypointAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group__5__Impl"


    // $ANTLR start "rule__Waypoint__Group_5__0"
    // InternalMazeDsl.g:4737:1: rule__Waypoint__Group_5__0 : rule__Waypoint__Group_5__0__Impl rule__Waypoint__Group_5__1 ;
    public final void rule__Waypoint__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4741:1: ( rule__Waypoint__Group_5__0__Impl rule__Waypoint__Group_5__1 )
            // InternalMazeDsl.g:4742:2: rule__Waypoint__Group_5__0__Impl rule__Waypoint__Group_5__1
            {
            pushFollow(FOLLOW_25);
            rule__Waypoint__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group_5__0"


    // $ANTLR start "rule__Waypoint__Group_5__0__Impl"
    // InternalMazeDsl.g:4749:1: rule__Waypoint__Group_5__0__Impl : ( ':' ) ;
    public final void rule__Waypoint__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4753:1: ( ( ':' ) )
            // InternalMazeDsl.g:4754:1: ( ':' )
            {
            // InternalMazeDsl.g:4754:1: ( ':' )
            // InternalMazeDsl.g:4755:2: ':'
            {
             before(grammarAccess.getWaypointAccess().getColonKeyword_5_0()); 
            match(input,75,FOLLOW_2); 
             after(grammarAccess.getWaypointAccess().getColonKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group_5__0__Impl"


    // $ANTLR start "rule__Waypoint__Group_5__1"
    // InternalMazeDsl.g:4764:1: rule__Waypoint__Group_5__1 : rule__Waypoint__Group_5__1__Impl rule__Waypoint__Group_5__2 ;
    public final void rule__Waypoint__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4768:1: ( rule__Waypoint__Group_5__1__Impl rule__Waypoint__Group_5__2 )
            // InternalMazeDsl.g:4769:2: rule__Waypoint__Group_5__1__Impl rule__Waypoint__Group_5__2
            {
            pushFollow(FOLLOW_43);
            rule__Waypoint__Group_5__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Waypoint__Group_5__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group_5__1"


    // $ANTLR start "rule__Waypoint__Group_5__1__Impl"
    // InternalMazeDsl.g:4776:1: rule__Waypoint__Group_5__1__Impl : ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) ) ;
    public final void rule__Waypoint__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4780:1: ( ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) ) )
            // InternalMazeDsl.g:4781:1: ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) )
            {
            // InternalMazeDsl.g:4781:1: ( ( rule__Waypoint__WaitTimeAssignment_5_1 ) )
            // InternalMazeDsl.g:4782:2: ( rule__Waypoint__WaitTimeAssignment_5_1 )
            {
             before(grammarAccess.getWaypointAccess().getWaitTimeAssignment_5_1()); 
            // InternalMazeDsl.g:4783:2: ( rule__Waypoint__WaitTimeAssignment_5_1 )
            // InternalMazeDsl.g:4783:3: rule__Waypoint__WaitTimeAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__Waypoint__WaitTimeAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getWaypointAccess().getWaitTimeAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group_5__1__Impl"


    // $ANTLR start "rule__Waypoint__Group_5__2"
    // InternalMazeDsl.g:4791:1: rule__Waypoint__Group_5__2 : rule__Waypoint__Group_5__2__Impl ;
    public final void rule__Waypoint__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4795:1: ( rule__Waypoint__Group_5__2__Impl )
            // InternalMazeDsl.g:4796:2: rule__Waypoint__Group_5__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Waypoint__Group_5__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group_5__2"


    // $ANTLR start "rule__Waypoint__Group_5__2__Impl"
    // InternalMazeDsl.g:4802:1: rule__Waypoint__Group_5__2__Impl : ( 'ms' ) ;
    public final void rule__Waypoint__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4806:1: ( ( 'ms' ) )
            // InternalMazeDsl.g:4807:1: ( 'ms' )
            {
            // InternalMazeDsl.g:4807:1: ( 'ms' )
            // InternalMazeDsl.g:4808:2: 'ms'
            {
             before(grammarAccess.getWaypointAccess().getMsKeyword_5_2()); 
            match(input,76,FOLLOW_2); 
             after(grammarAccess.getWaypointAccess().getMsKeyword_5_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__Group_5__2__Impl"


    // $ANTLR start "rule__LootTableConfig__Group__0"
    // InternalMazeDsl.g:4818:1: rule__LootTableConfig__Group__0 : rule__LootTableConfig__Group__0__Impl rule__LootTableConfig__Group__1 ;
    public final void rule__LootTableConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4822:1: ( rule__LootTableConfig__Group__0__Impl rule__LootTableConfig__Group__1 )
            // InternalMazeDsl.g:4823:2: rule__LootTableConfig__Group__0__Impl rule__LootTableConfig__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__LootTableConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__0"


    // $ANTLR start "rule__LootTableConfig__Group__0__Impl"
    // InternalMazeDsl.g:4830:1: rule__LootTableConfig__Group__0__Impl : ( 'loot-table' ) ;
    public final void rule__LootTableConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4834:1: ( ( 'loot-table' ) )
            // InternalMazeDsl.g:4835:1: ( 'loot-table' )
            {
            // InternalMazeDsl.g:4835:1: ( 'loot-table' )
            // InternalMazeDsl.g:4836:2: 'loot-table'
            {
             before(grammarAccess.getLootTableConfigAccess().getLootTableKeyword_0()); 
            match(input,77,FOLLOW_2); 
             after(grammarAccess.getLootTableConfigAccess().getLootTableKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__0__Impl"


    // $ANTLR start "rule__LootTableConfig__Group__1"
    // InternalMazeDsl.g:4845:1: rule__LootTableConfig__Group__1 : rule__LootTableConfig__Group__1__Impl rule__LootTableConfig__Group__2 ;
    public final void rule__LootTableConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4849:1: ( rule__LootTableConfig__Group__1__Impl rule__LootTableConfig__Group__2 )
            // InternalMazeDsl.g:4850:2: rule__LootTableConfig__Group__1__Impl rule__LootTableConfig__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__LootTableConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__1"


    // $ANTLR start "rule__LootTableConfig__Group__1__Impl"
    // InternalMazeDsl.g:4857:1: rule__LootTableConfig__Group__1__Impl : ( ( rule__LootTableConfig__NameAssignment_1 ) ) ;
    public final void rule__LootTableConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4861:1: ( ( ( rule__LootTableConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:4862:1: ( ( rule__LootTableConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:4862:1: ( ( rule__LootTableConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:4863:2: ( rule__LootTableConfig__NameAssignment_1 )
            {
             before(grammarAccess.getLootTableConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:4864:2: ( rule__LootTableConfig__NameAssignment_1 )
            // InternalMazeDsl.g:4864:3: rule__LootTableConfig__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__LootTableConfig__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getLootTableConfigAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__1__Impl"


    // $ANTLR start "rule__LootTableConfig__Group__2"
    // InternalMazeDsl.g:4872:1: rule__LootTableConfig__Group__2 : rule__LootTableConfig__Group__2__Impl rule__LootTableConfig__Group__3 ;
    public final void rule__LootTableConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4876:1: ( rule__LootTableConfig__Group__2__Impl rule__LootTableConfig__Group__3 )
            // InternalMazeDsl.g:4877:2: rule__LootTableConfig__Group__2__Impl rule__LootTableConfig__Group__3
            {
            pushFollow(FOLLOW_44);
            rule__LootTableConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__2"


    // $ANTLR start "rule__LootTableConfig__Group__2__Impl"
    // InternalMazeDsl.g:4884:1: rule__LootTableConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__LootTableConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4888:1: ( ( '{' ) )
            // InternalMazeDsl.g:4889:1: ( '{' )
            {
            // InternalMazeDsl.g:4889:1: ( '{' )
            // InternalMazeDsl.g:4890:2: '{'
            {
             before(grammarAccess.getLootTableConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getLootTableConfigAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__2__Impl"


    // $ANTLR start "rule__LootTableConfig__Group__3"
    // InternalMazeDsl.g:4899:1: rule__LootTableConfig__Group__3 : rule__LootTableConfig__Group__3__Impl rule__LootTableConfig__Group__4 ;
    public final void rule__LootTableConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4903:1: ( rule__LootTableConfig__Group__3__Impl rule__LootTableConfig__Group__4 )
            // InternalMazeDsl.g:4904:2: rule__LootTableConfig__Group__3__Impl rule__LootTableConfig__Group__4
            {
            pushFollow(FOLLOW_44);
            rule__LootTableConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__3"


    // $ANTLR start "rule__LootTableConfig__Group__3__Impl"
    // InternalMazeDsl.g:4911:1: rule__LootTableConfig__Group__3__Impl : ( ( rule__LootTableConfig__Group_3__0 )? ) ;
    public final void rule__LootTableConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4915:1: ( ( ( rule__LootTableConfig__Group_3__0 )? ) )
            // InternalMazeDsl.g:4916:1: ( ( rule__LootTableConfig__Group_3__0 )? )
            {
            // InternalMazeDsl.g:4916:1: ( ( rule__LootTableConfig__Group_3__0 )? )
            // InternalMazeDsl.g:4917:2: ( rule__LootTableConfig__Group_3__0 )?
            {
             before(grammarAccess.getLootTableConfigAccess().getGroup_3()); 
            // InternalMazeDsl.g:4918:2: ( rule__LootTableConfig__Group_3__0 )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==78) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalMazeDsl.g:4918:3: rule__LootTableConfig__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__LootTableConfig__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLootTableConfigAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__3__Impl"


    // $ANTLR start "rule__LootTableConfig__Group__4"
    // InternalMazeDsl.g:4926:1: rule__LootTableConfig__Group__4 : rule__LootTableConfig__Group__4__Impl rule__LootTableConfig__Group__5 ;
    public final void rule__LootTableConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4930:1: ( rule__LootTableConfig__Group__4__Impl rule__LootTableConfig__Group__5 )
            // InternalMazeDsl.g:4931:2: rule__LootTableConfig__Group__4__Impl rule__LootTableConfig__Group__5
            {
            pushFollow(FOLLOW_36);
            rule__LootTableConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__4"


    // $ANTLR start "rule__LootTableConfig__Group__4__Impl"
    // InternalMazeDsl.g:4938:1: rule__LootTableConfig__Group__4__Impl : ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) ) ;
    public final void rule__LootTableConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4942:1: ( ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) ) )
            // InternalMazeDsl.g:4943:1: ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) )
            {
            // InternalMazeDsl.g:4943:1: ( ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* ) )
            // InternalMazeDsl.g:4944:2: ( ( rule__LootTableConfig__ItemsAssignment_4 ) ) ( ( rule__LootTableConfig__ItemsAssignment_4 )* )
            {
            // InternalMazeDsl.g:4944:2: ( ( rule__LootTableConfig__ItemsAssignment_4 ) )
            // InternalMazeDsl.g:4945:3: ( rule__LootTableConfig__ItemsAssignment_4 )
            {
             before(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 
            // InternalMazeDsl.g:4946:3: ( rule__LootTableConfig__ItemsAssignment_4 )
            // InternalMazeDsl.g:4946:4: rule__LootTableConfig__ItemsAssignment_4
            {
            pushFollow(FOLLOW_45);
            rule__LootTableConfig__ItemsAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 

            }

            // InternalMazeDsl.g:4949:2: ( ( rule__LootTableConfig__ItemsAssignment_4 )* )
            // InternalMazeDsl.g:4950:3: ( rule__LootTableConfig__ItemsAssignment_4 )*
            {
             before(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 
            // InternalMazeDsl.g:4951:3: ( rule__LootTableConfig__ItemsAssignment_4 )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==79) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalMazeDsl.g:4951:4: rule__LootTableConfig__ItemsAssignment_4
            	    {
            	    pushFollow(FOLLOW_45);
            	    rule__LootTableConfig__ItemsAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

             after(grammarAccess.getLootTableConfigAccess().getItemsAssignment_4()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__4__Impl"


    // $ANTLR start "rule__LootTableConfig__Group__5"
    // InternalMazeDsl.g:4960:1: rule__LootTableConfig__Group__5 : rule__LootTableConfig__Group__5__Impl ;
    public final void rule__LootTableConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4964:1: ( rule__LootTableConfig__Group__5__Impl )
            // InternalMazeDsl.g:4965:2: rule__LootTableConfig__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__5"


    // $ANTLR start "rule__LootTableConfig__Group__5__Impl"
    // InternalMazeDsl.g:4971:1: rule__LootTableConfig__Group__5__Impl : ( '}' ) ;
    public final void rule__LootTableConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4975:1: ( ( '}' ) )
            // InternalMazeDsl.g:4976:1: ( '}' )
            {
            // InternalMazeDsl.g:4976:1: ( '}' )
            // InternalMazeDsl.g:4977:2: '}'
            {
             before(grammarAccess.getLootTableConfigAccess().getRightCurlyBracketKeyword_5()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getLootTableConfigAccess().getRightCurlyBracketKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group__5__Impl"


    // $ANTLR start "rule__LootTableConfig__Group_3__0"
    // InternalMazeDsl.g:4987:1: rule__LootTableConfig__Group_3__0 : rule__LootTableConfig__Group_3__0__Impl rule__LootTableConfig__Group_3__1 ;
    public final void rule__LootTableConfig__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:4991:1: ( rule__LootTableConfig__Group_3__0__Impl rule__LootTableConfig__Group_3__1 )
            // InternalMazeDsl.g:4992:2: rule__LootTableConfig__Group_3__0__Impl rule__LootTableConfig__Group_3__1
            {
            pushFollow(FOLLOW_25);
            rule__LootTableConfig__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group_3__0"


    // $ANTLR start "rule__LootTableConfig__Group_3__0__Impl"
    // InternalMazeDsl.g:4999:1: rule__LootTableConfig__Group_3__0__Impl : ( 'capacity' ) ;
    public final void rule__LootTableConfig__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5003:1: ( ( 'capacity' ) )
            // InternalMazeDsl.g:5004:1: ( 'capacity' )
            {
            // InternalMazeDsl.g:5004:1: ( 'capacity' )
            // InternalMazeDsl.g:5005:2: 'capacity'
            {
             before(grammarAccess.getLootTableConfigAccess().getCapacityKeyword_3_0()); 
            match(input,78,FOLLOW_2); 
             after(grammarAccess.getLootTableConfigAccess().getCapacityKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group_3__0__Impl"


    // $ANTLR start "rule__LootTableConfig__Group_3__1"
    // InternalMazeDsl.g:5014:1: rule__LootTableConfig__Group_3__1 : rule__LootTableConfig__Group_3__1__Impl ;
    public final void rule__LootTableConfig__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5018:1: ( rule__LootTableConfig__Group_3__1__Impl )
            // InternalMazeDsl.g:5019:2: rule__LootTableConfig__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LootTableConfig__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group_3__1"


    // $ANTLR start "rule__LootTableConfig__Group_3__1__Impl"
    // InternalMazeDsl.g:5025:1: rule__LootTableConfig__Group_3__1__Impl : ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) ) ;
    public final void rule__LootTableConfig__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5029:1: ( ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) ) )
            // InternalMazeDsl.g:5030:1: ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) )
            {
            // InternalMazeDsl.g:5030:1: ( ( rule__LootTableConfig__CapacityAssignment_3_1 ) )
            // InternalMazeDsl.g:5031:2: ( rule__LootTableConfig__CapacityAssignment_3_1 )
            {
             before(grammarAccess.getLootTableConfigAccess().getCapacityAssignment_3_1()); 
            // InternalMazeDsl.g:5032:2: ( rule__LootTableConfig__CapacityAssignment_3_1 )
            // InternalMazeDsl.g:5032:3: rule__LootTableConfig__CapacityAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__LootTableConfig__CapacityAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getLootTableConfigAccess().getCapacityAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__Group_3__1__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__0"
    // InternalMazeDsl.g:5041:1: rule__LootItemConfig__Group__0 : rule__LootItemConfig__Group__0__Impl rule__LootItemConfig__Group__1 ;
    public final void rule__LootItemConfig__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5045:1: ( rule__LootItemConfig__Group__0__Impl rule__LootItemConfig__Group__1 )
            // InternalMazeDsl.g:5046:2: rule__LootItemConfig__Group__0__Impl rule__LootItemConfig__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__LootItemConfig__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__0"


    // $ANTLR start "rule__LootItemConfig__Group__0__Impl"
    // InternalMazeDsl.g:5053:1: rule__LootItemConfig__Group__0__Impl : ( 'item' ) ;
    public final void rule__LootItemConfig__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5057:1: ( ( 'item' ) )
            // InternalMazeDsl.g:5058:1: ( 'item' )
            {
            // InternalMazeDsl.g:5058:1: ( 'item' )
            // InternalMazeDsl.g:5059:2: 'item'
            {
             before(grammarAccess.getLootItemConfigAccess().getItemKeyword_0()); 
            match(input,79,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getItemKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__0__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__1"
    // InternalMazeDsl.g:5068:1: rule__LootItemConfig__Group__1 : rule__LootItemConfig__Group__1__Impl rule__LootItemConfig__Group__2 ;
    public final void rule__LootItemConfig__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5072:1: ( rule__LootItemConfig__Group__1__Impl rule__LootItemConfig__Group__2 )
            // InternalMazeDsl.g:5073:2: rule__LootItemConfig__Group__1__Impl rule__LootItemConfig__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__LootItemConfig__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__1"


    // $ANTLR start "rule__LootItemConfig__Group__1__Impl"
    // InternalMazeDsl.g:5080:1: rule__LootItemConfig__Group__1__Impl : ( ( rule__LootItemConfig__NameAssignment_1 ) ) ;
    public final void rule__LootItemConfig__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5084:1: ( ( ( rule__LootItemConfig__NameAssignment_1 ) ) )
            // InternalMazeDsl.g:5085:1: ( ( rule__LootItemConfig__NameAssignment_1 ) )
            {
            // InternalMazeDsl.g:5085:1: ( ( rule__LootItemConfig__NameAssignment_1 ) )
            // InternalMazeDsl.g:5086:2: ( rule__LootItemConfig__NameAssignment_1 )
            {
             before(grammarAccess.getLootItemConfigAccess().getNameAssignment_1()); 
            // InternalMazeDsl.g:5087:2: ( rule__LootItemConfig__NameAssignment_1 )
            // InternalMazeDsl.g:5087:3: rule__LootItemConfig__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getLootItemConfigAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__1__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__2"
    // InternalMazeDsl.g:5095:1: rule__LootItemConfig__Group__2 : rule__LootItemConfig__Group__2__Impl rule__LootItemConfig__Group__3 ;
    public final void rule__LootItemConfig__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5099:1: ( rule__LootItemConfig__Group__2__Impl rule__LootItemConfig__Group__3 )
            // InternalMazeDsl.g:5100:2: rule__LootItemConfig__Group__2__Impl rule__LootItemConfig__Group__3
            {
            pushFollow(FOLLOW_19);
            rule__LootItemConfig__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__2"


    // $ANTLR start "rule__LootItemConfig__Group__2__Impl"
    // InternalMazeDsl.g:5107:1: rule__LootItemConfig__Group__2__Impl : ( '{' ) ;
    public final void rule__LootItemConfig__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5111:1: ( ( '{' ) )
            // InternalMazeDsl.g:5112:1: ( '{' )
            {
            // InternalMazeDsl.g:5112:1: ( '{' )
            // InternalMazeDsl.g:5113:2: '{'
            {
             before(grammarAccess.getLootItemConfigAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,31,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__2__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__3"
    // InternalMazeDsl.g:5122:1: rule__LootItemConfig__Group__3 : rule__LootItemConfig__Group__3__Impl rule__LootItemConfig__Group__4 ;
    public final void rule__LootItemConfig__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5126:1: ( rule__LootItemConfig__Group__3__Impl rule__LootItemConfig__Group__4 )
            // InternalMazeDsl.g:5127:2: rule__LootItemConfig__Group__3__Impl rule__LootItemConfig__Group__4
            {
            pushFollow(FOLLOW_46);
            rule__LootItemConfig__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__3"


    // $ANTLR start "rule__LootItemConfig__Group__3__Impl"
    // InternalMazeDsl.g:5134:1: rule__LootItemConfig__Group__3__Impl : ( 'type' ) ;
    public final void rule__LootItemConfig__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5138:1: ( ( 'type' ) )
            // InternalMazeDsl.g:5139:1: ( 'type' )
            {
            // InternalMazeDsl.g:5139:1: ( 'type' )
            // InternalMazeDsl.g:5140:2: 'type'
            {
             before(grammarAccess.getLootItemConfigAccess().getTypeKeyword_3()); 
            match(input,43,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getTypeKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__3__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__4"
    // InternalMazeDsl.g:5149:1: rule__LootItemConfig__Group__4 : rule__LootItemConfig__Group__4__Impl rule__LootItemConfig__Group__5 ;
    public final void rule__LootItemConfig__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5153:1: ( rule__LootItemConfig__Group__4__Impl rule__LootItemConfig__Group__5 )
            // InternalMazeDsl.g:5154:2: rule__LootItemConfig__Group__4__Impl rule__LootItemConfig__Group__5
            {
            pushFollow(FOLLOW_47);
            rule__LootItemConfig__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__4"


    // $ANTLR start "rule__LootItemConfig__Group__4__Impl"
    // InternalMazeDsl.g:5161:1: rule__LootItemConfig__Group__4__Impl : ( ( rule__LootItemConfig__TypeAssignment_4 ) ) ;
    public final void rule__LootItemConfig__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5165:1: ( ( ( rule__LootItemConfig__TypeAssignment_4 ) ) )
            // InternalMazeDsl.g:5166:1: ( ( rule__LootItemConfig__TypeAssignment_4 ) )
            {
            // InternalMazeDsl.g:5166:1: ( ( rule__LootItemConfig__TypeAssignment_4 ) )
            // InternalMazeDsl.g:5167:2: ( rule__LootItemConfig__TypeAssignment_4 )
            {
             before(grammarAccess.getLootItemConfigAccess().getTypeAssignment_4()); 
            // InternalMazeDsl.g:5168:2: ( rule__LootItemConfig__TypeAssignment_4 )
            // InternalMazeDsl.g:5168:3: rule__LootItemConfig__TypeAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__TypeAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getLootItemConfigAccess().getTypeAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__4__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__5"
    // InternalMazeDsl.g:5176:1: rule__LootItemConfig__Group__5 : rule__LootItemConfig__Group__5__Impl rule__LootItemConfig__Group__6 ;
    public final void rule__LootItemConfig__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5180:1: ( rule__LootItemConfig__Group__5__Impl rule__LootItemConfig__Group__6 )
            // InternalMazeDsl.g:5181:2: rule__LootItemConfig__Group__5__Impl rule__LootItemConfig__Group__6
            {
            pushFollow(FOLLOW_25);
            rule__LootItemConfig__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__5"


    // $ANTLR start "rule__LootItemConfig__Group__5__Impl"
    // InternalMazeDsl.g:5188:1: rule__LootItemConfig__Group__5__Impl : ( 'value' ) ;
    public final void rule__LootItemConfig__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5192:1: ( ( 'value' ) )
            // InternalMazeDsl.g:5193:1: ( 'value' )
            {
            // InternalMazeDsl.g:5193:1: ( 'value' )
            // InternalMazeDsl.g:5194:2: 'value'
            {
             before(grammarAccess.getLootItemConfigAccess().getValueKeyword_5()); 
            match(input,80,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getValueKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__5__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__6"
    // InternalMazeDsl.g:5203:1: rule__LootItemConfig__Group__6 : rule__LootItemConfig__Group__6__Impl rule__LootItemConfig__Group__7 ;
    public final void rule__LootItemConfig__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5207:1: ( rule__LootItemConfig__Group__6__Impl rule__LootItemConfig__Group__7 )
            // InternalMazeDsl.g:5208:2: rule__LootItemConfig__Group__6__Impl rule__LootItemConfig__Group__7
            {
            pushFollow(FOLLOW_48);
            rule__LootItemConfig__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__6"


    // $ANTLR start "rule__LootItemConfig__Group__6__Impl"
    // InternalMazeDsl.g:5215:1: rule__LootItemConfig__Group__6__Impl : ( ( rule__LootItemConfig__ValueAssignment_6 ) ) ;
    public final void rule__LootItemConfig__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5219:1: ( ( ( rule__LootItemConfig__ValueAssignment_6 ) ) )
            // InternalMazeDsl.g:5220:1: ( ( rule__LootItemConfig__ValueAssignment_6 ) )
            {
            // InternalMazeDsl.g:5220:1: ( ( rule__LootItemConfig__ValueAssignment_6 ) )
            // InternalMazeDsl.g:5221:2: ( rule__LootItemConfig__ValueAssignment_6 )
            {
             before(grammarAccess.getLootItemConfigAccess().getValueAssignment_6()); 
            // InternalMazeDsl.g:5222:2: ( rule__LootItemConfig__ValueAssignment_6 )
            // InternalMazeDsl.g:5222:3: rule__LootItemConfig__ValueAssignment_6
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__ValueAssignment_6();

            state._fsp--;


            }

             after(grammarAccess.getLootItemConfigAccess().getValueAssignment_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__6__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__7"
    // InternalMazeDsl.g:5230:1: rule__LootItemConfig__Group__7 : rule__LootItemConfig__Group__7__Impl rule__LootItemConfig__Group__8 ;
    public final void rule__LootItemConfig__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5234:1: ( rule__LootItemConfig__Group__7__Impl rule__LootItemConfig__Group__8 )
            // InternalMazeDsl.g:5235:2: rule__LootItemConfig__Group__7__Impl rule__LootItemConfig__Group__8
            {
            pushFollow(FOLLOW_48);
            rule__LootItemConfig__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__7"


    // $ANTLR start "rule__LootItemConfig__Group__7__Impl"
    // InternalMazeDsl.g:5242:1: rule__LootItemConfig__Group__7__Impl : ( ( rule__LootItemConfig__Group_7__0 )? ) ;
    public final void rule__LootItemConfig__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5246:1: ( ( ( rule__LootItemConfig__Group_7__0 )? ) )
            // InternalMazeDsl.g:5247:1: ( ( rule__LootItemConfig__Group_7__0 )? )
            {
            // InternalMazeDsl.g:5247:1: ( ( rule__LootItemConfig__Group_7__0 )? )
            // InternalMazeDsl.g:5248:2: ( rule__LootItemConfig__Group_7__0 )?
            {
             before(grammarAccess.getLootItemConfigAccess().getGroup_7()); 
            // InternalMazeDsl.g:5249:2: ( rule__LootItemConfig__Group_7__0 )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==81) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // InternalMazeDsl.g:5249:3: rule__LootItemConfig__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__LootItemConfig__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLootItemConfigAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__7__Impl"


    // $ANTLR start "rule__LootItemConfig__Group__8"
    // InternalMazeDsl.g:5257:1: rule__LootItemConfig__Group__8 : rule__LootItemConfig__Group__8__Impl ;
    public final void rule__LootItemConfig__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5261:1: ( rule__LootItemConfig__Group__8__Impl )
            // InternalMazeDsl.g:5262:2: rule__LootItemConfig__Group__8__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group__8__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__8"


    // $ANTLR start "rule__LootItemConfig__Group__8__Impl"
    // InternalMazeDsl.g:5268:1: rule__LootItemConfig__Group__8__Impl : ( '}' ) ;
    public final void rule__LootItemConfig__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5272:1: ( ( '}' ) )
            // InternalMazeDsl.g:5273:1: ( '}' )
            {
            // InternalMazeDsl.g:5273:1: ( '}' )
            // InternalMazeDsl.g:5274:2: '}'
            {
             before(grammarAccess.getLootItemConfigAccess().getRightCurlyBracketKeyword_8()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getRightCurlyBracketKeyword_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group__8__Impl"


    // $ANTLR start "rule__LootItemConfig__Group_7__0"
    // InternalMazeDsl.g:5284:1: rule__LootItemConfig__Group_7__0 : rule__LootItemConfig__Group_7__0__Impl rule__LootItemConfig__Group_7__1 ;
    public final void rule__LootItemConfig__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5288:1: ( rule__LootItemConfig__Group_7__0__Impl rule__LootItemConfig__Group_7__1 )
            // InternalMazeDsl.g:5289:2: rule__LootItemConfig__Group_7__0__Impl rule__LootItemConfig__Group_7__1
            {
            pushFollow(FOLLOW_25);
            rule__LootItemConfig__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group_7__0"


    // $ANTLR start "rule__LootItemConfig__Group_7__0__Impl"
    // InternalMazeDsl.g:5296:1: rule__LootItemConfig__Group_7__0__Impl : ( 'weight' ) ;
    public final void rule__LootItemConfig__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5300:1: ( ( 'weight' ) )
            // InternalMazeDsl.g:5301:1: ( 'weight' )
            {
            // InternalMazeDsl.g:5301:1: ( 'weight' )
            // InternalMazeDsl.g:5302:2: 'weight'
            {
             before(grammarAccess.getLootItemConfigAccess().getWeightKeyword_7_0()); 
            match(input,81,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getWeightKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group_7__0__Impl"


    // $ANTLR start "rule__LootItemConfig__Group_7__1"
    // InternalMazeDsl.g:5311:1: rule__LootItemConfig__Group_7__1 : rule__LootItemConfig__Group_7__1__Impl ;
    public final void rule__LootItemConfig__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5315:1: ( rule__LootItemConfig__Group_7__1__Impl )
            // InternalMazeDsl.g:5316:2: rule__LootItemConfig__Group_7__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group_7__1"


    // $ANTLR start "rule__LootItemConfig__Group_7__1__Impl"
    // InternalMazeDsl.g:5322:1: rule__LootItemConfig__Group_7__1__Impl : ( ( rule__LootItemConfig__WeightAssignment_7_1 ) ) ;
    public final void rule__LootItemConfig__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5326:1: ( ( ( rule__LootItemConfig__WeightAssignment_7_1 ) ) )
            // InternalMazeDsl.g:5327:1: ( ( rule__LootItemConfig__WeightAssignment_7_1 ) )
            {
            // InternalMazeDsl.g:5327:1: ( ( rule__LootItemConfig__WeightAssignment_7_1 ) )
            // InternalMazeDsl.g:5328:2: ( rule__LootItemConfig__WeightAssignment_7_1 )
            {
             before(grammarAccess.getLootItemConfigAccess().getWeightAssignment_7_1()); 
            // InternalMazeDsl.g:5329:2: ( rule__LootItemConfig__WeightAssignment_7_1 )
            // InternalMazeDsl.g:5329:3: rule__LootItemConfig__WeightAssignment_7_1
            {
            pushFollow(FOLLOW_2);
            rule__LootItemConfig__WeightAssignment_7_1();

            state._fsp--;


            }

             after(grammarAccess.getLootItemConfigAccess().getWeightAssignment_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__Group_7__1__Impl"


    // $ANTLR start "rule__DOUBLE__Group__0"
    // InternalMazeDsl.g:5338:1: rule__DOUBLE__Group__0 : rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1 ;
    public final void rule__DOUBLE__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5342:1: ( rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1 )
            // InternalMazeDsl.g:5343:2: rule__DOUBLE__Group__0__Impl rule__DOUBLE__Group__1
            {
            pushFollow(FOLLOW_16);
            rule__DOUBLE__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__0"


    // $ANTLR start "rule__DOUBLE__Group__0__Impl"
    // InternalMazeDsl.g:5350:1: rule__DOUBLE__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__DOUBLE__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5354:1: ( ( ( '-' )? ) )
            // InternalMazeDsl.g:5355:1: ( ( '-' )? )
            {
            // InternalMazeDsl.g:5355:1: ( ( '-' )? )
            // InternalMazeDsl.g:5356:2: ( '-' )?
            {
             before(grammarAccess.getDOUBLEAccess().getHyphenMinusKeyword_0()); 
            // InternalMazeDsl.g:5357:2: ( '-' )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==82) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalMazeDsl.g:5357:3: '-'
                    {
                    match(input,82,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getDOUBLEAccess().getHyphenMinusKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__0__Impl"


    // $ANTLR start "rule__DOUBLE__Group__1"
    // InternalMazeDsl.g:5365:1: rule__DOUBLE__Group__1 : rule__DOUBLE__Group__1__Impl rule__DOUBLE__Group__2 ;
    public final void rule__DOUBLE__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5369:1: ( rule__DOUBLE__Group__1__Impl rule__DOUBLE__Group__2 )
            // InternalMazeDsl.g:5370:2: rule__DOUBLE__Group__1__Impl rule__DOUBLE__Group__2
            {
            pushFollow(FOLLOW_49);
            rule__DOUBLE__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__1"


    // $ANTLR start "rule__DOUBLE__Group__1__Impl"
    // InternalMazeDsl.g:5377:1: rule__DOUBLE__Group__1__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5381:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5382:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:5382:1: ( RULE_INT )
            // InternalMazeDsl.g:5383:2: RULE_INT
            {
             before(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_1()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__1__Impl"


    // $ANTLR start "rule__DOUBLE__Group__2"
    // InternalMazeDsl.g:5392:1: rule__DOUBLE__Group__2 : rule__DOUBLE__Group__2__Impl ;
    public final void rule__DOUBLE__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5396:1: ( rule__DOUBLE__Group__2__Impl )
            // InternalMazeDsl.g:5397:2: rule__DOUBLE__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__2"


    // $ANTLR start "rule__DOUBLE__Group__2__Impl"
    // InternalMazeDsl.g:5403:1: rule__DOUBLE__Group__2__Impl : ( ( rule__DOUBLE__Group_2__0 )? ) ;
    public final void rule__DOUBLE__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5407:1: ( ( ( rule__DOUBLE__Group_2__0 )? ) )
            // InternalMazeDsl.g:5408:1: ( ( rule__DOUBLE__Group_2__0 )? )
            {
            // InternalMazeDsl.g:5408:1: ( ( rule__DOUBLE__Group_2__0 )? )
            // InternalMazeDsl.g:5409:2: ( rule__DOUBLE__Group_2__0 )?
            {
             before(grammarAccess.getDOUBLEAccess().getGroup_2()); 
            // InternalMazeDsl.g:5410:2: ( rule__DOUBLE__Group_2__0 )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==83) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalMazeDsl.g:5410:3: rule__DOUBLE__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__DOUBLE__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getDOUBLEAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group__2__Impl"


    // $ANTLR start "rule__DOUBLE__Group_2__0"
    // InternalMazeDsl.g:5419:1: rule__DOUBLE__Group_2__0 : rule__DOUBLE__Group_2__0__Impl rule__DOUBLE__Group_2__1 ;
    public final void rule__DOUBLE__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5423:1: ( rule__DOUBLE__Group_2__0__Impl rule__DOUBLE__Group_2__1 )
            // InternalMazeDsl.g:5424:2: rule__DOUBLE__Group_2__0__Impl rule__DOUBLE__Group_2__1
            {
            pushFollow(FOLLOW_25);
            rule__DOUBLE__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_2__0"


    // $ANTLR start "rule__DOUBLE__Group_2__0__Impl"
    // InternalMazeDsl.g:5431:1: rule__DOUBLE__Group_2__0__Impl : ( '.' ) ;
    public final void rule__DOUBLE__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5435:1: ( ( '.' ) )
            // InternalMazeDsl.g:5436:1: ( '.' )
            {
            // InternalMazeDsl.g:5436:1: ( '.' )
            // InternalMazeDsl.g:5437:2: '.'
            {
             before(grammarAccess.getDOUBLEAccess().getFullStopKeyword_2_0()); 
            match(input,83,FOLLOW_2); 
             after(grammarAccess.getDOUBLEAccess().getFullStopKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_2__0__Impl"


    // $ANTLR start "rule__DOUBLE__Group_2__1"
    // InternalMazeDsl.g:5446:1: rule__DOUBLE__Group_2__1 : rule__DOUBLE__Group_2__1__Impl ;
    public final void rule__DOUBLE__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5450:1: ( rule__DOUBLE__Group_2__1__Impl )
            // InternalMazeDsl.g:5451:2: rule__DOUBLE__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__DOUBLE__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_2__1"


    // $ANTLR start "rule__DOUBLE__Group_2__1__Impl"
    // InternalMazeDsl.g:5457:1: rule__DOUBLE__Group_2__1__Impl : ( RULE_INT ) ;
    public final void rule__DOUBLE__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5461:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5462:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:5462:1: ( RULE_INT )
            // InternalMazeDsl.g:5463:2: RULE_INT
            {
             before(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_2_1()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getDOUBLEAccess().getINTTerminalRuleCall_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DOUBLE__Group_2__1__Impl"


    // $ANTLR start "rule__SIGNED_INT__Group__0"
    // InternalMazeDsl.g:5473:1: rule__SIGNED_INT__Group__0 : rule__SIGNED_INT__Group__0__Impl rule__SIGNED_INT__Group__1 ;
    public final void rule__SIGNED_INT__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5477:1: ( rule__SIGNED_INT__Group__0__Impl rule__SIGNED_INT__Group__1 )
            // InternalMazeDsl.g:5478:2: rule__SIGNED_INT__Group__0__Impl rule__SIGNED_INT__Group__1
            {
            pushFollow(FOLLOW_16);
            rule__SIGNED_INT__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SIGNED_INT__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SIGNED_INT__Group__0"


    // $ANTLR start "rule__SIGNED_INT__Group__0__Impl"
    // InternalMazeDsl.g:5485:1: rule__SIGNED_INT__Group__0__Impl : ( ( '-' )? ) ;
    public final void rule__SIGNED_INT__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5489:1: ( ( ( '-' )? ) )
            // InternalMazeDsl.g:5490:1: ( ( '-' )? )
            {
            // InternalMazeDsl.g:5490:1: ( ( '-' )? )
            // InternalMazeDsl.g:5491:2: ( '-' )?
            {
             before(grammarAccess.getSIGNED_INTAccess().getHyphenMinusKeyword_0()); 
            // InternalMazeDsl.g:5492:2: ( '-' )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==82) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalMazeDsl.g:5492:3: '-'
                    {
                    match(input,82,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getSIGNED_INTAccess().getHyphenMinusKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SIGNED_INT__Group__0__Impl"


    // $ANTLR start "rule__SIGNED_INT__Group__1"
    // InternalMazeDsl.g:5500:1: rule__SIGNED_INT__Group__1 : rule__SIGNED_INT__Group__1__Impl ;
    public final void rule__SIGNED_INT__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5504:1: ( rule__SIGNED_INT__Group__1__Impl )
            // InternalMazeDsl.g:5505:2: rule__SIGNED_INT__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SIGNED_INT__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SIGNED_INT__Group__1"


    // $ANTLR start "rule__SIGNED_INT__Group__1__Impl"
    // InternalMazeDsl.g:5511:1: rule__SIGNED_INT__Group__1__Impl : ( RULE_INT ) ;
    public final void rule__SIGNED_INT__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5515:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5516:1: ( RULE_INT )
            {
            // InternalMazeDsl.g:5516:1: ( RULE_INT )
            // InternalMazeDsl.g:5517:2: RULE_INT
            {
             before(grammarAccess.getSIGNED_INTAccess().getINTTerminalRuleCall_1()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getSIGNED_INTAccess().getINTTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SIGNED_INT__Group__1__Impl"


    // $ANTLR start "rule__GameConfiguration__NameAssignment_1"
    // InternalMazeDsl.g:5527:1: rule__GameConfiguration__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__GameConfiguration__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5531:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:5532:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:5532:2: ( RULE_ID )
            // InternalMazeDsl.g:5533:3: RULE_ID
            {
             before(grammarAccess.getGameConfigurationAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getGameConfigurationAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__NameAssignment_1"


    // $ANTLR start "rule__GameConfiguration__ImportsAssignment_3"
    // InternalMazeDsl.g:5542:1: rule__GameConfiguration__ImportsAssignment_3 : ( ruleImport ) ;
    public final void rule__GameConfiguration__ImportsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5546:1: ( ( ruleImport ) )
            // InternalMazeDsl.g:5547:2: ( ruleImport )
            {
            // InternalMazeDsl.g:5547:2: ( ruleImport )
            // InternalMazeDsl.g:5548:3: ruleImport
            {
             before(grammarAccess.getGameConfigurationAccess().getImportsImportParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleImport();

            state._fsp--;

             after(grammarAccess.getGameConfigurationAccess().getImportsImportParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__ImportsAssignment_3"


    // $ANTLR start "rule__GameConfiguration__DifficultyAssignment_4"
    // InternalMazeDsl.g:5557:1: rule__GameConfiguration__DifficultyAssignment_4 : ( ruleDifficultyConfig ) ;
    public final void rule__GameConfiguration__DifficultyAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5561:1: ( ( ruleDifficultyConfig ) )
            // InternalMazeDsl.g:5562:2: ( ruleDifficultyConfig )
            {
            // InternalMazeDsl.g:5562:2: ( ruleDifficultyConfig )
            // InternalMazeDsl.g:5563:3: ruleDifficultyConfig
            {
             before(grammarAccess.getGameConfigurationAccess().getDifficultyDifficultyConfigParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleDifficultyConfig();

            state._fsp--;

             after(grammarAccess.getGameConfigurationAccess().getDifficultyDifficultyConfigParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__DifficultyAssignment_4"


    // $ANTLR start "rule__GameConfiguration__OpponentsAssignment_5"
    // InternalMazeDsl.g:5572:1: rule__GameConfiguration__OpponentsAssignment_5 : ( ruleOpponentConfig ) ;
    public final void rule__GameConfiguration__OpponentsAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5576:1: ( ( ruleOpponentConfig ) )
            // InternalMazeDsl.g:5577:2: ( ruleOpponentConfig )
            {
            // InternalMazeDsl.g:5577:2: ( ruleOpponentConfig )
            // InternalMazeDsl.g:5578:3: ruleOpponentConfig
            {
             before(grammarAccess.getGameConfigurationAccess().getOpponentsOpponentConfigParserRuleCall_5_0()); 
            pushFollow(FOLLOW_2);
            ruleOpponentConfig();

            state._fsp--;

             after(grammarAccess.getGameConfigurationAccess().getOpponentsOpponentConfigParserRuleCall_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__OpponentsAssignment_5"


    // $ANTLR start "rule__GameConfiguration__PatrolsAssignment_6"
    // InternalMazeDsl.g:5587:1: rule__GameConfiguration__PatrolsAssignment_6 : ( rulePatrolConfig ) ;
    public final void rule__GameConfiguration__PatrolsAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5591:1: ( ( rulePatrolConfig ) )
            // InternalMazeDsl.g:5592:2: ( rulePatrolConfig )
            {
            // InternalMazeDsl.g:5592:2: ( rulePatrolConfig )
            // InternalMazeDsl.g:5593:3: rulePatrolConfig
            {
             before(grammarAccess.getGameConfigurationAccess().getPatrolsPatrolConfigParserRuleCall_6_0()); 
            pushFollow(FOLLOW_2);
            rulePatrolConfig();

            state._fsp--;

             after(grammarAccess.getGameConfigurationAccess().getPatrolsPatrolConfigParserRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__PatrolsAssignment_6"


    // $ANTLR start "rule__GameConfiguration__LootTablesAssignment_7"
    // InternalMazeDsl.g:5602:1: rule__GameConfiguration__LootTablesAssignment_7 : ( ruleLootTableConfig ) ;
    public final void rule__GameConfiguration__LootTablesAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5606:1: ( ( ruleLootTableConfig ) )
            // InternalMazeDsl.g:5607:2: ( ruleLootTableConfig )
            {
            // InternalMazeDsl.g:5607:2: ( ruleLootTableConfig )
            // InternalMazeDsl.g:5608:3: ruleLootTableConfig
            {
             before(grammarAccess.getGameConfigurationAccess().getLootTablesLootTableConfigParserRuleCall_7_0()); 
            pushFollow(FOLLOW_2);
            ruleLootTableConfig();

            state._fsp--;

             after(grammarAccess.getGameConfigurationAccess().getLootTablesLootTableConfigParserRuleCall_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GameConfiguration__LootTablesAssignment_7"


    // $ANTLR start "rule__Import__ImportURIAssignment_1"
    // InternalMazeDsl.g:5617:1: rule__Import__ImportURIAssignment_1 : ( RULE_STRING ) ;
    public final void rule__Import__ImportURIAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5621:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:5622:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:5622:2: ( RULE_STRING )
            // InternalMazeDsl.g:5623:3: RULE_STRING
            {
             before(grammarAccess.getImportAccess().getImportURISTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getImportAccess().getImportURISTRINGTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__ImportURIAssignment_1"


    // $ANTLR start "rule__DifficultyConfig__LevelAssignment_3"
    // InternalMazeDsl.g:5632:1: rule__DifficultyConfig__LevelAssignment_3 : ( ruleDifficultyLevel ) ;
    public final void rule__DifficultyConfig__LevelAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5636:1: ( ( ruleDifficultyLevel ) )
            // InternalMazeDsl.g:5637:2: ( ruleDifficultyLevel )
            {
            // InternalMazeDsl.g:5637:2: ( ruleDifficultyLevel )
            // InternalMazeDsl.g:5638:3: ruleDifficultyLevel
            {
             before(grammarAccess.getDifficultyConfigAccess().getLevelDifficultyLevelEnumRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleDifficultyLevel();

            state._fsp--;

             after(grammarAccess.getDifficultyConfigAccess().getLevelDifficultyLevelEnumRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__LevelAssignment_3"


    // $ANTLR start "rule__DifficultyConfig__InstantDeathAssignment_4_1"
    // InternalMazeDsl.g:5647:1: rule__DifficultyConfig__InstantDeathAssignment_4_1 : ( ( 'true' ) ) ;
    public final void rule__DifficultyConfig__InstantDeathAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5651:1: ( ( ( 'true' ) ) )
            // InternalMazeDsl.g:5652:2: ( ( 'true' ) )
            {
            // InternalMazeDsl.g:5652:2: ( ( 'true' ) )
            // InternalMazeDsl.g:5653:3: ( 'true' )
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathTrueKeyword_4_1_0()); 
            // InternalMazeDsl.g:5654:3: ( 'true' )
            // InternalMazeDsl.g:5655:4: 'true'
            {
             before(grammarAccess.getDifficultyConfigAccess().getInstantDeathTrueKeyword_4_1_0()); 
            match(input,11,FOLLOW_2); 
             after(grammarAccess.getDifficultyConfigAccess().getInstantDeathTrueKeyword_4_1_0()); 

            }

             after(grammarAccess.getDifficultyConfigAccess().getInstantDeathTrueKeyword_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__InstantDeathAssignment_4_1"


    // $ANTLR start "rule__DifficultyConfig__SpeedMultiplierAssignment_5_1"
    // InternalMazeDsl.g:5666:1: rule__DifficultyConfig__SpeedMultiplierAssignment_5_1 : ( ruleDOUBLE ) ;
    public final void rule__DifficultyConfig__SpeedMultiplierAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5670:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:5671:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:5671:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:5672:3: ruleDOUBLE
            {
             before(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierDOUBLEParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getDifficultyConfigAccess().getSpeedMultiplierDOUBLEParserRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__SpeedMultiplierAssignment_5_1"


    // $ANTLR start "rule__DifficultyConfig__DamageMultiplierAssignment_6_1"
    // InternalMazeDsl.g:5681:1: rule__DifficultyConfig__DamageMultiplierAssignment_6_1 : ( ruleDOUBLE ) ;
    public final void rule__DifficultyConfig__DamageMultiplierAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5685:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:5686:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:5686:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:5687:3: ruleDOUBLE
            {
             before(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierDOUBLEParserRuleCall_6_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getDifficultyConfigAccess().getDamageMultiplierDOUBLEParserRuleCall_6_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__DamageMultiplierAssignment_6_1"


    // $ANTLR start "rule__DifficultyConfig__MaxThreatAssignment_7_1"
    // InternalMazeDsl.g:5696:1: rule__DifficultyConfig__MaxThreatAssignment_7_1 : ( ruleSIGNED_INT ) ;
    public final void rule__DifficultyConfig__MaxThreatAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5700:1: ( ( ruleSIGNED_INT ) )
            // InternalMazeDsl.g:5701:2: ( ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:5701:2: ( ruleSIGNED_INT )
            // InternalMazeDsl.g:5702:3: ruleSIGNED_INT
            {
             before(grammarAccess.getDifficultyConfigAccess().getMaxThreatSIGNED_INTParserRuleCall_7_1_0()); 
            pushFollow(FOLLOW_2);
            ruleSIGNED_INT();

            state._fsp--;

             after(grammarAccess.getDifficultyConfigAccess().getMaxThreatSIGNED_INTParserRuleCall_7_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__MaxThreatAssignment_7_1"


    // $ANTLR start "rule__DifficultyConfig__EnemyLimitsAssignment_8"
    // InternalMazeDsl.g:5711:1: rule__DifficultyConfig__EnemyLimitsAssignment_8 : ( ruleEnemyLimit ) ;
    public final void rule__DifficultyConfig__EnemyLimitsAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5715:1: ( ( ruleEnemyLimit ) )
            // InternalMazeDsl.g:5716:2: ( ruleEnemyLimit )
            {
            // InternalMazeDsl.g:5716:2: ( ruleEnemyLimit )
            // InternalMazeDsl.g:5717:3: ruleEnemyLimit
            {
             before(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsEnemyLimitParserRuleCall_8_0()); 
            pushFollow(FOLLOW_2);
            ruleEnemyLimit();

            state._fsp--;

             after(grammarAccess.getDifficultyConfigAccess().getEnemyLimitsEnemyLimitParserRuleCall_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DifficultyConfig__EnemyLimitsAssignment_8"


    // $ANTLR start "rule__EnemyLimit__TypeAssignment_1"
    // InternalMazeDsl.g:5726:1: rule__EnemyLimit__TypeAssignment_1 : ( ruleEnemyType ) ;
    public final void rule__EnemyLimit__TypeAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5730:1: ( ( ruleEnemyType ) )
            // InternalMazeDsl.g:5731:2: ( ruleEnemyType )
            {
            // InternalMazeDsl.g:5731:2: ( ruleEnemyType )
            // InternalMazeDsl.g:5732:3: ruleEnemyType
            {
             before(grammarAccess.getEnemyLimitAccess().getTypeEnemyTypeEnumRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleEnemyType();

            state._fsp--;

             after(grammarAccess.getEnemyLimitAccess().getTypeEnemyTypeEnumRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__TypeAssignment_1"


    // $ANTLR start "rule__EnemyLimit__MaxCountAssignment_3"
    // InternalMazeDsl.g:5741:1: rule__EnemyLimit__MaxCountAssignment_3 : ( ruleSIGNED_INT ) ;
    public final void rule__EnemyLimit__MaxCountAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5745:1: ( ( ruleSIGNED_INT ) )
            // InternalMazeDsl.g:5746:2: ( ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:5746:2: ( ruleSIGNED_INT )
            // InternalMazeDsl.g:5747:3: ruleSIGNED_INT
            {
             before(grammarAccess.getEnemyLimitAccess().getMaxCountSIGNED_INTParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleSIGNED_INT();

            state._fsp--;

             after(grammarAccess.getEnemyLimitAccess().getMaxCountSIGNED_INTParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__EnemyLimit__MaxCountAssignment_3"


    // $ANTLR start "rule__OpponentConfig__NameAssignment_1"
    // InternalMazeDsl.g:5756:1: rule__OpponentConfig__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__OpponentConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5760:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:5761:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:5761:2: ( RULE_ID )
            // InternalMazeDsl.g:5762:3: RULE_ID
            {
             before(grammarAccess.getOpponentConfigAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__NameAssignment_1"


    // $ANTLR start "rule__OpponentConfig__TypeAssignment_4"
    // InternalMazeDsl.g:5771:1: rule__OpponentConfig__TypeAssignment_4 : ( ruleCharacterTypeEnum ) ;
    public final void rule__OpponentConfig__TypeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5775:1: ( ( ruleCharacterTypeEnum ) )
            // InternalMazeDsl.g:5776:2: ( ruleCharacterTypeEnum )
            {
            // InternalMazeDsl.g:5776:2: ( ruleCharacterTypeEnum )
            // InternalMazeDsl.g:5777:3: ruleCharacterTypeEnum
            {
             before(grammarAccess.getOpponentConfigAccess().getTypeCharacterTypeEnumEnumRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleCharacterTypeEnum();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getTypeCharacterTypeEnumEnumRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__TypeAssignment_4"


    // $ANTLR start "rule__OpponentConfig__DisplayNameAssignment_5_1"
    // InternalMazeDsl.g:5786:1: rule__OpponentConfig__DisplayNameAssignment_5_1 : ( RULE_STRING ) ;
    public final void rule__OpponentConfig__DisplayNameAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5790:1: ( ( RULE_STRING ) )
            // InternalMazeDsl.g:5791:2: ( RULE_STRING )
            {
            // InternalMazeDsl.g:5791:2: ( RULE_STRING )
            // InternalMazeDsl.g:5792:3: RULE_STRING
            {
             before(grammarAccess.getOpponentConfigAccess().getDisplayNameSTRINGTerminalRuleCall_5_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getDisplayNameSTRINGTerminalRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__DisplayNameAssignment_5_1"


    // $ANTLR start "rule__OpponentConfig__HealthAssignment_6_1"
    // InternalMazeDsl.g:5801:1: rule__OpponentConfig__HealthAssignment_6_1 : ( ruleSIGNED_INT ) ;
    public final void rule__OpponentConfig__HealthAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5805:1: ( ( ruleSIGNED_INT ) )
            // InternalMazeDsl.g:5806:2: ( ruleSIGNED_INT )
            {
            // InternalMazeDsl.g:5806:2: ( ruleSIGNED_INT )
            // InternalMazeDsl.g:5807:3: ruleSIGNED_INT
            {
             before(grammarAccess.getOpponentConfigAccess().getHealthSIGNED_INTParserRuleCall_6_1_0()); 
            pushFollow(FOLLOW_2);
            ruleSIGNED_INT();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getHealthSIGNED_INTParserRuleCall_6_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__HealthAssignment_6_1"


    // $ANTLR start "rule__OpponentConfig__SpeedAssignment_7_1"
    // InternalMazeDsl.g:5816:1: rule__OpponentConfig__SpeedAssignment_7_1 : ( ruleDOUBLE ) ;
    public final void rule__OpponentConfig__SpeedAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5820:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:5821:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:5821:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:5822:3: ruleDOUBLE
            {
             before(grammarAccess.getOpponentConfigAccess().getSpeedDOUBLEParserRuleCall_7_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getSpeedDOUBLEParserRuleCall_7_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__SpeedAssignment_7_1"


    // $ANTLR start "rule__OpponentConfig__ThreatLevelAssignment_8_1"
    // InternalMazeDsl.g:5831:1: rule__OpponentConfig__ThreatLevelAssignment_8_1 : ( ruleDOUBLE ) ;
    public final void rule__OpponentConfig__ThreatLevelAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5835:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:5836:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:5836:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:5837:3: ruleDOUBLE
            {
             before(grammarAccess.getOpponentConfigAccess().getThreatLevelDOUBLEParserRuleCall_8_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getThreatLevelDOUBLEParserRuleCall_8_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__ThreatLevelAssignment_8_1"


    // $ANTLR start "rule__OpponentConfig__EnabledAssignment_9_1"
    // InternalMazeDsl.g:5846:1: rule__OpponentConfig__EnabledAssignment_9_1 : ( ruleBOOLEAN ) ;
    public final void rule__OpponentConfig__EnabledAssignment_9_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5850:1: ( ( ruleBOOLEAN ) )
            // InternalMazeDsl.g:5851:2: ( ruleBOOLEAN )
            {
            // InternalMazeDsl.g:5851:2: ( ruleBOOLEAN )
            // InternalMazeDsl.g:5852:3: ruleBOOLEAN
            {
             before(grammarAccess.getOpponentConfigAccess().getEnabledBOOLEANParserRuleCall_9_1_0()); 
            pushFollow(FOLLOW_2);
            ruleBOOLEAN();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getEnabledBOOLEANParserRuleCall_9_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__EnabledAssignment_9_1"


    // $ANTLR start "rule__OpponentConfig__BehaviorAssignment_10_1"
    // InternalMazeDsl.g:5861:1: rule__OpponentConfig__BehaviorAssignment_10_1 : ( ruleBehaviorTypeEnum ) ;
    public final void rule__OpponentConfig__BehaviorAssignment_10_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5865:1: ( ( ruleBehaviorTypeEnum ) )
            // InternalMazeDsl.g:5866:2: ( ruleBehaviorTypeEnum )
            {
            // InternalMazeDsl.g:5866:2: ( ruleBehaviorTypeEnum )
            // InternalMazeDsl.g:5867:3: ruleBehaviorTypeEnum
            {
             before(grammarAccess.getOpponentConfigAccess().getBehaviorBehaviorTypeEnumEnumRuleCall_10_1_0()); 
            pushFollow(FOLLOW_2);
            ruleBehaviorTypeEnum();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getBehaviorBehaviorTypeEnumEnumRuleCall_10_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__BehaviorAssignment_10_1"


    // $ANTLR start "rule__OpponentConfig__CharacterSpecificsAssignment_11"
    // InternalMazeDsl.g:5876:1: rule__OpponentConfig__CharacterSpecificsAssignment_11 : ( ruleCharacterSpecifics ) ;
    public final void rule__OpponentConfig__CharacterSpecificsAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5880:1: ( ( ruleCharacterSpecifics ) )
            // InternalMazeDsl.g:5881:2: ( ruleCharacterSpecifics )
            {
            // InternalMazeDsl.g:5881:2: ( ruleCharacterSpecifics )
            // InternalMazeDsl.g:5882:3: ruleCharacterSpecifics
            {
             before(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsCharacterSpecificsParserRuleCall_11_0()); 
            pushFollow(FOLLOW_2);
            ruleCharacterSpecifics();

            state._fsp--;

             after(grammarAccess.getOpponentConfigAccess().getCharacterSpecificsCharacterSpecificsParserRuleCall_11_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__CharacterSpecificsAssignment_11"


    // $ANTLR start "rule__OpponentConfig__PatrolRefAssignment_12_1"
    // InternalMazeDsl.g:5891:1: rule__OpponentConfig__PatrolRefAssignment_12_1 : ( ( RULE_ID ) ) ;
    public final void rule__OpponentConfig__PatrolRefAssignment_12_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5895:1: ( ( ( RULE_ID ) ) )
            // InternalMazeDsl.g:5896:2: ( ( RULE_ID ) )
            {
            // InternalMazeDsl.g:5896:2: ( ( RULE_ID ) )
            // InternalMazeDsl.g:5897:3: ( RULE_ID )
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigCrossReference_12_1_0()); 
            // InternalMazeDsl.g:5898:3: ( RULE_ID )
            // InternalMazeDsl.g:5899:4: RULE_ID
            {
             before(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigIDTerminalRuleCall_12_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigIDTerminalRuleCall_12_1_0_1()); 

            }

             after(grammarAccess.getOpponentConfigAccess().getPatrolRefPatrolConfigCrossReference_12_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__PatrolRefAssignment_12_1"


    // $ANTLR start "rule__OpponentConfig__LootRefAssignment_13_1"
    // InternalMazeDsl.g:5910:1: rule__OpponentConfig__LootRefAssignment_13_1 : ( ( RULE_ID ) ) ;
    public final void rule__OpponentConfig__LootRefAssignment_13_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5914:1: ( ( ( RULE_ID ) ) )
            // InternalMazeDsl.g:5915:2: ( ( RULE_ID ) )
            {
            // InternalMazeDsl.g:5915:2: ( ( RULE_ID ) )
            // InternalMazeDsl.g:5916:3: ( RULE_ID )
            {
             before(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigCrossReference_13_1_0()); 
            // InternalMazeDsl.g:5917:3: ( RULE_ID )
            // InternalMazeDsl.g:5918:4: RULE_ID
            {
             before(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigIDTerminalRuleCall_13_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigIDTerminalRuleCall_13_1_0_1()); 

            }

             after(grammarAccess.getOpponentConfigAccess().getLootRefLootTableConfigCrossReference_13_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__OpponentConfig__LootRefAssignment_13_1"


    // $ANTLR start "rule__ZombieSpecifics__AttackDamageAssignment_3_1"
    // InternalMazeDsl.g:5929:1: rule__ZombieSpecifics__AttackDamageAssignment_3_1 : ( RULE_INT ) ;
    public final void rule__ZombieSpecifics__AttackDamageAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5933:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5934:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5934:2: ( RULE_INT )
            // InternalMazeDsl.g:5935:3: RULE_INT
            {
             before(grammarAccess.getZombieSpecificsAccess().getAttackDamageINTTerminalRuleCall_3_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getAttackDamageINTTerminalRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__AttackDamageAssignment_3_1"


    // $ANTLR start "rule__ZombieSpecifics__InfectionLevelAssignment_4_1"
    // InternalMazeDsl.g:5944:1: rule__ZombieSpecifics__InfectionLevelAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__ZombieSpecifics__InfectionLevelAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5948:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5949:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5949:2: ( RULE_INT )
            // InternalMazeDsl.g:5950:3: RULE_INT
            {
             before(grammarAccess.getZombieSpecificsAccess().getInfectionLevelINTTerminalRuleCall_4_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getInfectionLevelINTTerminalRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__InfectionLevelAssignment_4_1"


    // $ANTLR start "rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1"
    // InternalMazeDsl.g:5959:1: rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5963:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5964:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5964:2: ( RULE_INT )
            // InternalMazeDsl.g:5965:3: RULE_INT
            {
             before(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeINTTerminalRuleCall_5_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getZombieSpecificsAccess().getResurrectionTimeINTTerminalRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ZombieSpecifics__ResurrectionTimeAssignment_5_1"


    // $ANTLR start "rule__GhostSpecifics__AttackDamageAssignment_3_1"
    // InternalMazeDsl.g:5974:1: rule__GhostSpecifics__AttackDamageAssignment_3_1 : ( RULE_INT ) ;
    public final void rule__GhostSpecifics__AttackDamageAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5978:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5979:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5979:2: ( RULE_INT )
            // InternalMazeDsl.g:5980:3: RULE_INT
            {
             before(grammarAccess.getGhostSpecificsAccess().getAttackDamageINTTerminalRuleCall_3_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getAttackDamageINTTerminalRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__AttackDamageAssignment_3_1"


    // $ANTLR start "rule__GhostSpecifics__VisibilityLevelAssignment_4_1"
    // InternalMazeDsl.g:5989:1: rule__GhostSpecifics__VisibilityLevelAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__GhostSpecifics__VisibilityLevelAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:5993:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:5994:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:5994:2: ( RULE_INT )
            // InternalMazeDsl.g:5995:3: RULE_INT
            {
             before(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelINTTerminalRuleCall_4_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getGhostSpecificsAccess().getVisibilityLevelINTTerminalRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__VisibilityLevelAssignment_4_1"


    // $ANTLR start "rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1"
    // InternalMazeDsl.g:6004:1: rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1 : ( ruleDOUBLE ) ;
    public final void rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6008:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6009:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6009:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6010:3: ruleDOUBLE
            {
             before(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyDOUBLEParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getGhostSpecificsAccess().getNonTangibilityEnergyDOUBLEParserRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GhostSpecifics__NonTangibilityEnergyAssignment_5_1"


    // $ANTLR start "rule__RangedSpecifics__AttackRangeAssignment_3_1"
    // InternalMazeDsl.g:6019:1: rule__RangedSpecifics__AttackRangeAssignment_3_1 : ( ruleDOUBLE ) ;
    public final void rule__RangedSpecifics__AttackRangeAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6023:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6024:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6024:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6025:3: ruleDOUBLE
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackRangeDOUBLEParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getRangedSpecificsAccess().getAttackRangeDOUBLEParserRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__AttackRangeAssignment_3_1"


    // $ANTLR start "rule__RangedSpecifics__AttackCooldownAssignment_4_1"
    // InternalMazeDsl.g:6034:1: rule__RangedSpecifics__AttackCooldownAssignment_4_1 : ( RULE_INT ) ;
    public final void rule__RangedSpecifics__AttackCooldownAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6038:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:6039:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:6039:2: ( RULE_INT )
            // InternalMazeDsl.g:6040:3: RULE_INT
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackCooldownINTTerminalRuleCall_4_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getAttackCooldownINTTerminalRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__AttackCooldownAssignment_4_1"


    // $ANTLR start "rule__RangedSpecifics__AttackDamageAssignment_5_1"
    // InternalMazeDsl.g:6049:1: rule__RangedSpecifics__AttackDamageAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__RangedSpecifics__AttackDamageAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6053:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:6054:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:6054:2: ( RULE_INT )
            // InternalMazeDsl.g:6055:3: RULE_INT
            {
             before(grammarAccess.getRangedSpecificsAccess().getAttackDamageINTTerminalRuleCall_5_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getRangedSpecificsAccess().getAttackDamageINTTerminalRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__AttackDamageAssignment_5_1"


    // $ANTLR start "rule__RangedSpecifics__ProjectileSpeedAssignment_6_1"
    // InternalMazeDsl.g:6064:1: rule__RangedSpecifics__ProjectileSpeedAssignment_6_1 : ( ruleDOUBLE ) ;
    public final void rule__RangedSpecifics__ProjectileSpeedAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6068:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6069:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6069:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6070:3: ruleDOUBLE
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedDOUBLEParserRuleCall_6_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getRangedSpecificsAccess().getProjectileSpeedDOUBLEParserRuleCall_6_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__ProjectileSpeedAssignment_6_1"


    // $ANTLR start "rule__RangedSpecifics__ProjectileTypeAssignment_7_1"
    // InternalMazeDsl.g:6079:1: rule__RangedSpecifics__ProjectileTypeAssignment_7_1 : ( ruleProjectileTypeEnum ) ;
    public final void rule__RangedSpecifics__ProjectileTypeAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6083:1: ( ( ruleProjectileTypeEnum ) )
            // InternalMazeDsl.g:6084:2: ( ruleProjectileTypeEnum )
            {
            // InternalMazeDsl.g:6084:2: ( ruleProjectileTypeEnum )
            // InternalMazeDsl.g:6085:3: ruleProjectileTypeEnum
            {
             before(grammarAccess.getRangedSpecificsAccess().getProjectileTypeProjectileTypeEnumEnumRuleCall_7_1_0()); 
            pushFollow(FOLLOW_2);
            ruleProjectileTypeEnum();

            state._fsp--;

             after(grammarAccess.getRangedSpecificsAccess().getProjectileTypeProjectileTypeEnumEnumRuleCall_7_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__ProjectileTypeAssignment_7_1"


    // $ANTLR start "rule__RangedSpecifics__SplashRadiusAssignment_8_1"
    // InternalMazeDsl.g:6094:1: rule__RangedSpecifics__SplashRadiusAssignment_8_1 : ( ruleDOUBLE ) ;
    public final void rule__RangedSpecifics__SplashRadiusAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6098:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6099:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6099:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6100:3: ruleDOUBLE
            {
             before(grammarAccess.getRangedSpecificsAccess().getSplashRadiusDOUBLEParserRuleCall_8_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getRangedSpecificsAccess().getSplashRadiusDOUBLEParserRuleCall_8_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RangedSpecifics__SplashRadiusAssignment_8_1"


    // $ANTLR start "rule__PatrolConfig__NameAssignment_1"
    // InternalMazeDsl.g:6109:1: rule__PatrolConfig__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__PatrolConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6113:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:6114:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:6114:2: ( RULE_ID )
            // InternalMazeDsl.g:6115:3: RULE_ID
            {
             before(grammarAccess.getPatrolConfigAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getPatrolConfigAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__NameAssignment_1"


    // $ANTLR start "rule__PatrolConfig__VisionRangeAssignment_3_1"
    // InternalMazeDsl.g:6124:1: rule__PatrolConfig__VisionRangeAssignment_3_1 : ( ruleDOUBLE ) ;
    public final void rule__PatrolConfig__VisionRangeAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6128:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6129:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6129:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6130:3: ruleDOUBLE
            {
             before(grammarAccess.getPatrolConfigAccess().getVisionRangeDOUBLEParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getPatrolConfigAccess().getVisionRangeDOUBLEParserRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__VisionRangeAssignment_3_1"


    // $ANTLR start "rule__PatrolConfig__ZoneAssignment_4"
    // InternalMazeDsl.g:6139:1: rule__PatrolConfig__ZoneAssignment_4 : ( rulePatrolZoneConfig ) ;
    public final void rule__PatrolConfig__ZoneAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6143:1: ( ( rulePatrolZoneConfig ) )
            // InternalMazeDsl.g:6144:2: ( rulePatrolZoneConfig )
            {
            // InternalMazeDsl.g:6144:2: ( rulePatrolZoneConfig )
            // InternalMazeDsl.g:6145:3: rulePatrolZoneConfig
            {
             before(grammarAccess.getPatrolConfigAccess().getZonePatrolZoneConfigParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            rulePatrolZoneConfig();

            state._fsp--;

             after(grammarAccess.getPatrolConfigAccess().getZonePatrolZoneConfigParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__ZoneAssignment_4"


    // $ANTLR start "rule__PatrolConfig__WaypointsAssignment_7"
    // InternalMazeDsl.g:6154:1: rule__PatrolConfig__WaypointsAssignment_7 : ( ruleWaypoint ) ;
    public final void rule__PatrolConfig__WaypointsAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6158:1: ( ( ruleWaypoint ) )
            // InternalMazeDsl.g:6159:2: ( ruleWaypoint )
            {
            // InternalMazeDsl.g:6159:2: ( ruleWaypoint )
            // InternalMazeDsl.g:6160:3: ruleWaypoint
            {
             before(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_7_0()); 
            pushFollow(FOLLOW_2);
            ruleWaypoint();

            state._fsp--;

             after(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__WaypointsAssignment_7"


    // $ANTLR start "rule__PatrolConfig__WaypointsAssignment_8_1"
    // InternalMazeDsl.g:6169:1: rule__PatrolConfig__WaypointsAssignment_8_1 : ( ruleWaypoint ) ;
    public final void rule__PatrolConfig__WaypointsAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6173:1: ( ( ruleWaypoint ) )
            // InternalMazeDsl.g:6174:2: ( ruleWaypoint )
            {
            // InternalMazeDsl.g:6174:2: ( ruleWaypoint )
            // InternalMazeDsl.g:6175:3: ruleWaypoint
            {
             before(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_8_1_0()); 
            pushFollow(FOLLOW_2);
            ruleWaypoint();

            state._fsp--;

             after(grammarAccess.getPatrolConfigAccess().getWaypointsWaypointParserRuleCall_8_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolConfig__WaypointsAssignment_8_1"


    // $ANTLR start "rule__PatrolZoneConfig__TopLeftXAssignment_4"
    // InternalMazeDsl.g:6184:1: rule__PatrolZoneConfig__TopLeftXAssignment_4 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__TopLeftXAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6188:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6189:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6189:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6190:3: ruleDOUBLE
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXDOUBLEParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getPatrolZoneConfigAccess().getTopLeftXDOUBLEParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__TopLeftXAssignment_4"


    // $ANTLR start "rule__PatrolZoneConfig__TopLeftYAssignment_6"
    // InternalMazeDsl.g:6199:1: rule__PatrolZoneConfig__TopLeftYAssignment_6 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__TopLeftYAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6203:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6204:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6204:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6205:3: ruleDOUBLE
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYDOUBLEParserRuleCall_6_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getPatrolZoneConfigAccess().getTopLeftYDOUBLEParserRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__TopLeftYAssignment_6"


    // $ANTLR start "rule__PatrolZoneConfig__WidthAssignment_9"
    // InternalMazeDsl.g:6214:1: rule__PatrolZoneConfig__WidthAssignment_9 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__WidthAssignment_9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6218:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6219:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6219:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6220:3: ruleDOUBLE
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getWidthDOUBLEParserRuleCall_9_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getPatrolZoneConfigAccess().getWidthDOUBLEParserRuleCall_9_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__WidthAssignment_9"


    // $ANTLR start "rule__PatrolZoneConfig__HeightAssignment_11"
    // InternalMazeDsl.g:6229:1: rule__PatrolZoneConfig__HeightAssignment_11 : ( ruleDOUBLE ) ;
    public final void rule__PatrolZoneConfig__HeightAssignment_11() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6233:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6234:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6234:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6235:3: ruleDOUBLE
            {
             before(grammarAccess.getPatrolZoneConfigAccess().getHeightDOUBLEParserRuleCall_11_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getPatrolZoneConfigAccess().getHeightDOUBLEParserRuleCall_11_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PatrolZoneConfig__HeightAssignment_11"


    // $ANTLR start "rule__Waypoint__XAssignment_1"
    // InternalMazeDsl.g:6244:1: rule__Waypoint__XAssignment_1 : ( ruleDOUBLE ) ;
    public final void rule__Waypoint__XAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6248:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6249:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6249:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6250:3: ruleDOUBLE
            {
             before(grammarAccess.getWaypointAccess().getXDOUBLEParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getWaypointAccess().getXDOUBLEParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__XAssignment_1"


    // $ANTLR start "rule__Waypoint__YAssignment_3"
    // InternalMazeDsl.g:6259:1: rule__Waypoint__YAssignment_3 : ( ruleDOUBLE ) ;
    public final void rule__Waypoint__YAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6263:1: ( ( ruleDOUBLE ) )
            // InternalMazeDsl.g:6264:2: ( ruleDOUBLE )
            {
            // InternalMazeDsl.g:6264:2: ( ruleDOUBLE )
            // InternalMazeDsl.g:6265:3: ruleDOUBLE
            {
             before(grammarAccess.getWaypointAccess().getYDOUBLEParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleDOUBLE();

            state._fsp--;

             after(grammarAccess.getWaypointAccess().getYDOUBLEParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__YAssignment_3"


    // $ANTLR start "rule__Waypoint__WaitTimeAssignment_5_1"
    // InternalMazeDsl.g:6274:1: rule__Waypoint__WaitTimeAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__Waypoint__WaitTimeAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6278:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:6279:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:6279:2: ( RULE_INT )
            // InternalMazeDsl.g:6280:3: RULE_INT
            {
             before(grammarAccess.getWaypointAccess().getWaitTimeINTTerminalRuleCall_5_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getWaypointAccess().getWaitTimeINTTerminalRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Waypoint__WaitTimeAssignment_5_1"


    // $ANTLR start "rule__LootTableConfig__NameAssignment_1"
    // InternalMazeDsl.g:6289:1: rule__LootTableConfig__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__LootTableConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6293:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:6294:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:6294:2: ( RULE_ID )
            // InternalMazeDsl.g:6295:3: RULE_ID
            {
             before(grammarAccess.getLootTableConfigAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getLootTableConfigAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__NameAssignment_1"


    // $ANTLR start "rule__LootTableConfig__CapacityAssignment_3_1"
    // InternalMazeDsl.g:6304:1: rule__LootTableConfig__CapacityAssignment_3_1 : ( RULE_INT ) ;
    public final void rule__LootTableConfig__CapacityAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6308:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:6309:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:6309:2: ( RULE_INT )
            // InternalMazeDsl.g:6310:3: RULE_INT
            {
             before(grammarAccess.getLootTableConfigAccess().getCapacityINTTerminalRuleCall_3_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getLootTableConfigAccess().getCapacityINTTerminalRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__CapacityAssignment_3_1"


    // $ANTLR start "rule__LootTableConfig__ItemsAssignment_4"
    // InternalMazeDsl.g:6319:1: rule__LootTableConfig__ItemsAssignment_4 : ( ruleLootItemConfig ) ;
    public final void rule__LootTableConfig__ItemsAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6323:1: ( ( ruleLootItemConfig ) )
            // InternalMazeDsl.g:6324:2: ( ruleLootItemConfig )
            {
            // InternalMazeDsl.g:6324:2: ( ruleLootItemConfig )
            // InternalMazeDsl.g:6325:3: ruleLootItemConfig
            {
             before(grammarAccess.getLootTableConfigAccess().getItemsLootItemConfigParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleLootItemConfig();

            state._fsp--;

             after(grammarAccess.getLootTableConfigAccess().getItemsLootItemConfigParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootTableConfig__ItemsAssignment_4"


    // $ANTLR start "rule__LootItemConfig__NameAssignment_1"
    // InternalMazeDsl.g:6334:1: rule__LootItemConfig__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__LootItemConfig__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6338:1: ( ( RULE_ID ) )
            // InternalMazeDsl.g:6339:2: ( RULE_ID )
            {
            // InternalMazeDsl.g:6339:2: ( RULE_ID )
            // InternalMazeDsl.g:6340:3: RULE_ID
            {
             before(grammarAccess.getLootItemConfigAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__NameAssignment_1"


    // $ANTLR start "rule__LootItemConfig__TypeAssignment_4"
    // InternalMazeDsl.g:6349:1: rule__LootItemConfig__TypeAssignment_4 : ( ruleLootItemTypeEnum ) ;
    public final void rule__LootItemConfig__TypeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6353:1: ( ( ruleLootItemTypeEnum ) )
            // InternalMazeDsl.g:6354:2: ( ruleLootItemTypeEnum )
            {
            // InternalMazeDsl.g:6354:2: ( ruleLootItemTypeEnum )
            // InternalMazeDsl.g:6355:3: ruleLootItemTypeEnum
            {
             before(grammarAccess.getLootItemConfigAccess().getTypeLootItemTypeEnumEnumRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleLootItemTypeEnum();

            state._fsp--;

             after(grammarAccess.getLootItemConfigAccess().getTypeLootItemTypeEnumEnumRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__TypeAssignment_4"


    // $ANTLR start "rule__LootItemConfig__ValueAssignment_6"
    // InternalMazeDsl.g:6364:1: rule__LootItemConfig__ValueAssignment_6 : ( RULE_INT ) ;
    public final void rule__LootItemConfig__ValueAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6368:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:6369:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:6369:2: ( RULE_INT )
            // InternalMazeDsl.g:6370:3: RULE_INT
            {
             before(grammarAccess.getLootItemConfigAccess().getValueINTTerminalRuleCall_6_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getValueINTTerminalRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__ValueAssignment_6"


    // $ANTLR start "rule__LootItemConfig__WeightAssignment_7_1"
    // InternalMazeDsl.g:6379:1: rule__LootItemConfig__WeightAssignment_7_1 : ( RULE_INT ) ;
    public final void rule__LootItemConfig__WeightAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalMazeDsl.g:6383:1: ( ( RULE_INT ) )
            // InternalMazeDsl.g:6384:2: ( RULE_INT )
            {
            // InternalMazeDsl.g:6384:2: ( RULE_INT )
            // InternalMazeDsl.g:6385:3: RULE_INT
            {
             before(grammarAccess.getLootItemConfigAccess().getWeightINTTerminalRuleCall_7_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getLootItemConfigAccess().getWeightINTTerminalRuleCall_7_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LootItemConfig__WeightAssignment_7_1"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000040700400000L,0x0000000000002000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x000000000000E000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x000001F100000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000010L,0x0000000000040000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x048FF00100400000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000780000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0070000100000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0310000100000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0488000000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0xF810000100000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000003800000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000029L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000014L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000000L,0x000000000000C000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x000000000000C000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x000000003C000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000100000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});

}
